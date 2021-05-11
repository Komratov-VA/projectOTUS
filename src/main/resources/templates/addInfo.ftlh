<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div> Привет, Клиент ${name}</div>
    <div>Заполни свой профиль чтобы найти своих друзей и твои друзья могли найти тебя</div>
    <div>
        <form method="post">
            <p><b>Ваша фамилия</b><Br>
                <input required type="text" name="firstName" placeholder="обязательное поле"/>
            </p><p><b>Ваше Имя</b><Br>
                <input required type="text" name="lastName" placeholder="обязательное поле"/>
            </p><p><b>Ваш возраст</b><Br>
                <input required type="text" size="40" name="age" pattern="[0-9]{0,3}" placeholder="обязательное поле, формат до 3 знаков"/>
            </p><p><b>Ваш город</b><Br>
                <input required type="text" name="city" placeholder="обязательное поле"/>
            </p>
            <p><b>Ваш пол</b><Br>
                <input required type="radio" name="gender" value="m"> мужской<Br>
                <input type="radio" name="gender" value="w"> женский<Br>
            </p><p><b>Ваши интересы</b><Br>
                <input type="text" name="hobby"/>
            </p>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Добавить</button>
        </form>
    </div>
    <@l.logout/>
</@c.page>

