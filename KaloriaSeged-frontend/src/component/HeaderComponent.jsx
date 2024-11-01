import React, {useEffect, useState} from "react";
import {isNullOrUndef} from "chart.js/helpers";

const HeaderComponent = () => {
    const [email, setEmail] = useState(localStorage.getItem("email"));
    const [token, setToken] = useState(localStorage.getItem("token"));

    useEffect(() => {
        const intervalId = setInterval(() => {
            const newEmail = localStorage.getItem("email");
            const newToken = localStorage.getItem("token");

            // Csak akkor állítjuk be az állapotot, ha az új értékek különböznek a régiektől
            if (newEmail !== email) setEmail(newEmail);
            if (newToken !== token) setToken(newToken);
        }, 100); // Ellenőrizzük 1 másodpercenként

        return () => clearInterval(intervalId); // Tisztítjuk az időzítőt, ha a komponens eltávolításra kerül
    }, [email, token]);


    function showLoginOrLogout() {
        console.log("email: ", email)
        console.log("token: ", token)
        if (isNullOrUndef(email) && isNullOrUndef(token)) {
            return <a className="navbar-toggler" href="/UserLogIn">Bejelentkezés/Regisztráció</a>
        }
        return <a className="navbar-toggler" href="/UserLogOut">Kijelentkezés</a>
    }

    return (
        <div>
            <header>
                <nav className="navbar navbar-dark bg-dark">
                    <a className="navbar-brand" href="/">Kalória Segéd</a>
                    <a className="navbar-toggler" href="/UserFoodLogs">Napló</a>
                    <a className="navbar-toggler" href="/Foods">Ételek</a>
                    <a className="navbar-toggler" href="/Users">Felhasználók</a>
                    {showLoginOrLogout()}
                </nav>
            </header>
        </div>
    )
}

export default HeaderComponent