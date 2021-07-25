<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div>
        Список пользователей которых удалось найти по фамилии ${filter!""}
    </div>
    </form>

<form method="post">
    <#list clientProfiles as profile>
        <div>
            <input type="checkbox" name=id value="${profile.id}">
            <b>id: ${profile.id}</b>
            <b>Имя: ${profile.firstName}</b>
            <b>Фамилий:${profile.lastName}</b>
            <b>Возраст: ${profile.age}</b>
            <b>Город: ${profile.city}</b>
            <b>Пол: ${profile.gender}</b>
            <b>Интересы: ${profile.hobby!""}</b>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">подписаться</button>
        <#else>
        Нет ни одного пользователя с такой фамилией
    </#list>
</form>
    <@l.profile/>
    <@l.logout/>
</@c.page>

