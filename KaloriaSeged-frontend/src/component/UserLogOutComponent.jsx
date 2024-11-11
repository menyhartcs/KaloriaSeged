import React, {useState} from "react";
import {getUserByEmail, loginUser} from "../service/UserService.js";
import {useNavigate} from "react-router-dom";
import moment from "moment/moment.js";

const UserLogOutComponent = () => {

    if (localStorage.getItem("email") !== undefined && localStorage.getItem("token")) {
        localStorage.removeItem("email")
        localStorage.removeItem("token")
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