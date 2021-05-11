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
            <input type="radio" name=id value="${profile.id}">
            <b>Имя: ${profile.firstName}</b>
<#--        <span>${message.text}</span>-->
            <b>Фамилий:${profile.lastName}</b>
            <b>Возраст: ${profile.age}</b>
            <b>Город: ${profile.city}</b>
            <b>Пол: ${profile.gender}</b>
            <b>Интересы: ${profile.hobby!""}</b>
<#--            <strong>${message.autorName}</strong>-->
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

