<#macro login path>
    <form action="${path}" method="post">
        <div><label> Имя : <input type="text" name="username"/> </label></div>
        <div><label> Пароль: <input type="password" name="password"/> </label></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Войти"/></div>
    </form>
</#macro>

<#macro reg path>
    <form action="${path}" method="post">
        <div><label> Имя : <input type="text" name="username"/> </label></div>
        <div><label> Пароль: <input type="password" name="password"/> </label></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Зарегистрироваться"/></div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Выйти"/>
    </form>
</#macro>

<#macro profile>
    <form action="/" method="get">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="На страницу профиля"/>
    </form>
</#macro>

<#macro post>
    <form action="/post" method="get">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Просмотреть посты моих друзей"/>
    </form>
</#macro>