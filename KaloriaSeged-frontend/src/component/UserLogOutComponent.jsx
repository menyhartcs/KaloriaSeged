import React from "react";
import {checkLoginStatus, logOutUser} from "../service/UserService.js";
import {isNullOrUndef} from "chart.js/helpers";
import {useNavigate} from "react-router-dom";

const UserLogOutComponent = () => {

    const currentEmail = localStorage.getItem("email")
    const navigator = useNavigate();

    checkLoginStatus(currentEmail).then((response) => {
        if (!isNullOrUndef(response.data.role)) {
            localStorage.removeItem("email")
            logOutUser()
            navigator("/UserLogIn");
        }
    }).catch(() => {
        console.log("ISMERETLEN FELHASZNÁLÓ")
        navigator("/UserLogIn");
    })

    return (
        <div className="d-flex justify-content-center align-items-center main-content" style={{height: "80vh"}}>
            <div className="text-center">
                <h1>Sikeres kijelentkezés!</h1>
            </div>
        </div>
    )
}

export default UserLogOutComponent