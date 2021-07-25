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
    <div>Имя:</div>
    <input type="text" name="filter" value="${filter!""}">
    <div>Фамилия:</div>
    <input type="text" name="filter2" value="${filter2!""}">
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
            <b>Фамилий:${profile.lastName}</b>
            <b>Возраст: ${profile.age}</b>
            <b>Город: ${profile.city}</b>
            <b>Пол: ${profile.gender}</b>
            <b>Интересы: ${profile.hobby!""}</b>
        </div>
        <#else>
        Вы ни на кого не подписаны
    </#list>
    <@l.post/>

    <form method="post" action="/profile">
        <div>Ваш пост:</div>
        <input type="text" name="postClient" value="${postClient!""}">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Добавить пост</button>
    </form>

    <#list listPostUser as post>
        <div>
            <b>Пост: ${post}</b>
        </div>
    <#else>
        Вы пока еще не постили постов
    </#list>

<@l.logout/>
</@c.page>

