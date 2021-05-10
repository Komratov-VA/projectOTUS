<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>

<#--<div>Страница профиля ${firstName} ${lastName} </div>-->
<#--<div>Возраст: ${age}</div>-->
<#--<div>Город: ${city}</div>-->
<#--<div>Пол: ${gender}</div>-->
<#--<div>Интересы: ${hobby}</div>-->
<#--<div>Ваш профиль видят другие пользователи</div>-->
<#--    <div>-->
<#--    Список пользователей-->
<#--</div>-->


<#--<form method="get" action="/search">-->
<#--    <input type="text" name="filter" value="${filter!""}">-->
<#--    <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
<#--    <button type="submit">Найти</button>-->
<#--</form>-->


    <div>
        Список пользователей которых удалось найти по фамилии ${filter!""}
    </div>
    </form>

<#--    <form method="post">-->
<#--        <p><b>Ваша фамилия</b><Br>-->
<#--            <input required type="text" name="last" placeholder="обязательное поле"/>-->
<#--        </p><p><b>Ваше Имя</b><Br>-->
<#--            <input required type="text" name="name" placeholder="обязательное поле"/>-->
<#--        </p><p><b>Ваше Отчество</b><Br>-->
<#--            <input type="text" name="sure" />-->
<#--        </p><p><b>Ваш возраст</b><Br>-->
<#--            <input required type="text" size="40" name="age" pattern="[0-9]{0,3}" placeholder="обязательное поле, формат до 3 знаков"/>-->
<#--        </p><p><b>Ваш город</b><Br>-->
<#--            <input required type="text" name="sity" placeholder="обязательное поле"/>-->
<#--        </p>-->
<#--        <p><b>Ваш пол</b><Br>-->
<#--            <input required type="radio" name="gender" value="m"> мужской<Br>-->
<#--            <input type="radio" name="gender" value="w"> женский<Br>-->
<#--        </p>-->
<#--        <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
<#--        <button type="submit">Добавить</button>-->
<#--    </form>-->
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

