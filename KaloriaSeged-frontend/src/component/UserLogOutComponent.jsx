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
        <div className="container">
            <br/><br/>
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <h1 className="text-center">Sikeres kijelentkezés!</h1>
                </div>
            </div>
        </div>
    )
}

export default UserLogOutComponent