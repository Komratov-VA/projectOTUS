<#import "parts/common.ftlh" as c>
<@c.page>
<div> Привет, Клиент ${name}</div>
<div>Заполни свой профиль чтобы найти своих друзей и твои друзья могли найти тебя</div>
<div>
    <form method="post">
        <p><b>Ваша фамилия</b><Br>
        <input required type="text" name="last" placeholder="обязательное поле"/>
        </p><p><b>Ваше Имя</b><Br>
        <input required type="text" name="name" placeholder="обязательное поле"/>
        </p><p><b>Ваше Отчество</b><Br>
        <input type="text" name="sure" />
        </p><p><b>Ваш возраст</b><Br>
        <input required type="text" size="40" name="age" pattern="[0-9]{0,3}" placeholder="обязательное поле, формат до 3 знаков"/>
        </p><p><b>Ваш город</b><Br>
        <input required type="text" name="sity" placeholder="обязательное поле"/>
        </p>
        <p><b>Ваш пол</b><Br>
        <input required type="radio" name="gender" value="m"> мужской<Br>
        <input type="radio" name="gender" value="w"> женский<Br>
        </p>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Добавить</button>
    </form>
</div>
<a href="/main">Главная страница</a>
</@c.page>

