<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
Регистрация нового пользователя
${message!""}
<@l.reg "/registration"/>
<a href="/login">На страницу входа</a>
</@c.page>
