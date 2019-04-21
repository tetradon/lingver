import i18n from "i18next";

i18n
    .init({
        // we init with resources
        resources: {
            en: {},
            ru: {
                translations: {
                    "Login": "Вход",
                    "Username": "Имя пользователя",
                    "Password": "Пароль",
                    "Sign in": "Войти",
                    "Sign up": "Регистрация",
                    "Do not have an account?": "Нет аккаунта?",
                    "Logout": "Выйти",
                    "First name": "Имя",
                    "Last name": "Фамилия",
                    "Repeat password": "Повторите пароль",
                    "Submit": "Подтвердить",
                    "You haven't added any words yet": "Вы пока не добавили никаких слов",
                    "Do it now!": "Сделайте это сейчас!",
                    "selected": "отмечено",
                    "Your dictionary": "Ваш словарь",
                    "Search": "Искать",
                    "Word": "Слово",
                    "Translation": "Перевод",
                    "Insert Date": "Дата добавления",
                    "Last Repeat Date": "Дата последнего повторения",
                    "Progress": "Прогресс",
                    "Rows per page": "Строк на странице",
                    "Delete": "Удалить",
                    "Train": "Тренировать",
                    "Word - Translation": "Слово - Перевод",
                    "Translation - Word": "Перевод - Слово",
                    "Add new word": "Добавить новое слово",
                    "Your variant": "Ваш вариант",
                    "Confirm delete": "Подтвердите удаление",
                    "Are you sure to delete selected items?": "Вы уверены в том, что хотите удалить отмеченные элементы?",
                    "Cancel": "Отмена",
                    "Confirm": "Подтвердить",
                    "Not bad!": "Неплохо!",
                    "Your result is": "Ваш результат",
                    "Next": "Далее",
                    "Not Found": "Не Найдено",
                    "Back": "Назад",
                    "Now you can sign in using credentials": "Теперь Вы можете войти используя имя пользователя и пароль",
                    "Welcome, ": "Добро пожаловать, ",
                    "Wrong credentials!": "Неверные имя пользователя или пароль!",
                    "Confirm closing": "Подтвердите закрытие",
                    "Are you sure? Your progress will be saved": "Вы уверены? Ваш прогресс будет сохранен"


                }
            }
        },
        lng: "ru",
        fallbackLng: "en",

        // have a common namespace used around the full app
        ns: ["translations"],
        defaultNS: "translations",

        keySeparator: false, // we use content as keys

        interpolation: {
            escapeValue: false
        }
    });

export default i18n;
