import React from "react";
import {logOutUser} from "../service/UserService.js";

const UserLogOutComponent = () => {

    if (localStorage.getItem("email") !== undefined && localStorage.getItem("token")) {
        localStorage.removeItem("email")
        localStorage.removeItem("token")
        logOutUser()
    }
    return (
        <div className="d-flex justify-content-center align-items-center main-content" style={{height: "80vh"}}>
            <div className="text-center">
                <h1>Sikeres kijelentkezés!</h1>
            </div>
        </div>
    )
}

export default UserLogOutComponent