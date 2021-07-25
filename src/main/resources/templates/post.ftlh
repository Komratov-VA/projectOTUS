<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div>
        Новостная лента друзей
    </div>
    </form>

    <#list friendsPost as profile>
        <div>
            <b>Имя: ${profile.firstName}</b>
            <b>Фамилий:${profile.lastName}</b>
            <b>Пост: ${profile.text}</b>
        </div>
        <#else>
        Ваши друзья не писали посты
    </#list>
    <@l.profile/>
    <@l.logout/>
</@c.page>

