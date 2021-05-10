<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>

<div>Страница профиля ${firstName} ${lastName} </div>
<div>Возраст: ${age}</div>
<div>Город: ${city}</div>
<div>Пол: ${gender}</div>
<div>Интересы: ${hobby}</div>
<div>Ваш профиль видят другие пользователи</div>
    <div>
    Список пользователей
</div>


<form method="get" action="/search">
    <input type="text" name="filter" value="${filter!""}">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit">Найти</button>
</form>


    <div>
        Список пользователей на которых вы подписаны
    </div>
    </form>
    <#list clientProfiles as profile>
        <div>
            <b>Имя: ${profile.firstName}</b>
<#--        <span>${message.text}</span>-->
            <b>Фамилий:${profile.lastName}</b>
            <b>Возраст: ${profile.age}</b>
            <b>Город: ${profile.city}</b>
            <b>Пол: ${profile.gender}</b>
            <b>Интересы: ${profile.hobby!""}</b>
<#--            <strong>${message.autorName}</strong>-->
        </div>
        <#else>
        Вы ни на кого не подписаны
    </#list>

<@l.logout/>
</@c.page>

