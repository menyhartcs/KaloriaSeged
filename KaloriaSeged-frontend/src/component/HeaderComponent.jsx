import React, {useEffect, useState} from "react";
import {isNullOrUndef} from "chart.js/helpers";
import {checkLoginStatus, getUserByEmail} from "../service/UserService.js";

const HeaderComponent = () => {
    const [email, setEmail] = useState(localStorage.getItem("email"));
    const [name, setName] = useState();
    const [role, setRole] = useState();

    useEffect(() => {
        const intervalId = setInterval(() => {
            const newEmail = localStorage.getItem("email");

            if (newEmail !== email) setEmail(newEmail);
        }, 100);

        return () => clearInterval(intervalId);
    }, [email]);


    useEffect(() => {
        if (email) {
            checkLoginStatus(email).then((response) => {
                if (!isNullOrUndef(response.data.role)) {
                    setName(response.data.name);
                    setRole(response.data.role);
                }
            }).catch(() => {
                console.log("ISMERETLEN FELHASZNÁLÓ")
            })
        }
    }, [email]);

    function showAdminPanel() {

        if (!isNullOrUndef(email) && role === "ROLE_ADMIN") {
            return (
                <>
                    <a className="navbar-toggler" href="/Users">Felhasználók</a>
                    <a className="navbar-toggler" href="/Exercises">Tevékenységek</a>
                    <a className="navbar-toggler" href="/Foods">Ételek</a>
                    <a className="navbar-brand">{name}</a>
                    <a className="navbar-toggler" href="/UserLogOut">Kijelentkezés</a>
                </>
            )
        }
    }

    function showLoginOrLogoutMenu() {

        if (isNullOrUndef(email)) {
            return <a className="navbar-toggler" href="/UserLogIn">Bejelentkezés/Regisztráció</a>
        }

        if (!isNullOrUndef(email) && role === "ROLE_USER") {
            return (
                <>
                    <a className="navbar-toggler" href="/Exercises">Tevékenységek</a>
                    <a className="navbar-toggler" href="/Foods">Ételek</a>
                    <a className="navbar-toggler" href="/UserLogs">Napló</a>
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