import React, {useEffect, useState} from "react";
import {isNullOrUndef} from "chart.js/helpers";
import {getUserByEmail} from "../service/UserService.js";

const HeaderComponent = () => {
    const [email, setEmail] = useState(localStorage.getItem("email"));
    const [name, setName] = useState();
    const [token, setToken] = useState(localStorage.getItem("token"));

    useEffect(() => {
        const intervalId = setInterval(() => {
            const newEmail = localStorage.getItem("email");
            const newToken = localStorage.getItem("token");

            if (newEmail !== email) setEmail(newEmail);
            if (newToken !== token) setToken(newToken);
        }, 100);

        return () => clearInterval(intervalId);
    }, [email, token]);


    useEffect(() => {
        if (email) {
            getUserByEmail(email).then((response) => {
                setName(response.data.name);
            });
        }
    }, [email]);

    function showAdminPanel() {

        if (!isNullOrUndef(email) && !isNullOrUndef(token) && email === "admin@mail.com") {
            return (
                <>
                    <a className="navbar-toggler" href="/Users">Felhasználók</a>
                    <a className="navbar-toggler" href="/Foods">Ételek</a>
                    <a className="navbar-brand">{name}</a>
                    <a className="navbar-toggler" href="/UserLogOut">Kijelentkezés</a>
                </>
            )
        }
    }

    function showLoginOrLogoutMenu() {

        if (isNullOrUndef(email) && isNullOrUndef(token)) {
            return <a className="navbar-toggler" href="/UserLogIn">Bejelentkezés/Regisztráció</a>
        }

        if (!isNullOrUndef(email) && !isNullOrUndef(token) && email !== "admin@mail.com") {
            return (
                <>
                    <a className="navbar-toggler" href="/UserFoodLogs">Napló</a>
                    <a className="navbar-toggler" href="/Foods">Ételek</a>
                    <a className="navbar-toggler" href="/CalorieCalculator">Kalória kalkulátor</a>
                    <a className="navbar-brand" href="/UserProfile">{name}</a>
                    <a className="navbar-toggler" href="/UserLogOut">Kijelentkezés</a>
                </>
            )
        }
    }

    return (
        <div>
            <header>
                <nav className="navbar navbar-dark bg-dark">
                    <a className="navbar-brand" href="/">Kalória Segéd</a>
                    {showAdminPanel()}
                    {showLoginOrLogoutMenu()}
                </nav>
            </header>
        </div>
    )
}

export default HeaderComponent