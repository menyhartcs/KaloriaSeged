import React from "react";

const HeaderComponent = () => {
    return (
        <div>
            <header>
                <nav className="navbar navbar-dark bg-dark">
                    <a className="navbar-brand" href="/">Kalória Segéd</a>
                    <a className="navbar-toggler" href="/Foods">Ételek</a>
                    <a className="navbar-toggler" href="/Users">Felhasználók</a>
                </nav>
            </header>
        </div>
    )
}

export default HeaderComponent