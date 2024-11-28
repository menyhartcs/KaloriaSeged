import React, {useEffect, useState} from "react";
import {loginUser} from "../service/UserService.js";
import {useNavigate} from "react-router-dom";
import moment from "moment/moment.js";
import {isNullOrUndef} from "chart.js/helpers";

const UserLogInComponent = () => {

    const [email, setEmail] = useState([]);
    const [password, setPassword] = useState([]);

    useEffect(() => {
        if (!isNullOrUndef(localStorage.getItem("email")) && !isNullOrUndef(localStorage.getItem("token"))) {
            navigator("/UserFoodLogs");
        }
    }, []);

    const [errors, setErrors] = useState({
        email: "",
        password: ""
    })

    const navigator = useNavigate();

    function getCurrentDate() {
        return moment().format('YYYY-MM-DD');
    }

    async function authenticateUser(e) {
        e.preventDefault();

        if (!validateForm()) return;

        try {

            const user = {
                email: email,
                password: password
            };

            const response = await loginUser(user);
            const token = response.data.token;

            localStorage.setItem("token", token);
            localStorage.setItem("email", user.email);

            console.log("Successful login");
            navigator("/UserFoodLogs");
        } catch (error) {
            if (error.response && error.response.data) {
                // Backend error message handling
                const backendError = error.response.data;
                setErrors((prevErrors) => ({ ...prevErrors, email: backendError }));
            } else {
                console.error("Ismeretlen hiba történt!", error);
            }
        }
    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {...errors}; // Create copy of errors

        if (!email.trim) {
            errorsCopy.email = "Email cím megadása kötelező!";
            valid = false;
        } else {
            const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            if (!emailRegex.test(email)) {
                errorsCopy.email = "Az email cím formátuma érvénytelen!";
                valid = false;
            } else {
                errorsCopy.email = ""; // Clearing error
            }
        }

        if (!password.trim) {
            errorsCopy.password = "Jelszó megadása kötelező!";
            valid = false;
        } else {
            const hasLetter = /[a-zA-Z]/.test(password);
            const hasDigit = /[0-9]/.test(password);

            if (password.length < 6) {
                errorsCopy.password = "A jelszónak legalább 6 karakter hosszúnak kell lennie!";
                valid = false;
            } else if (!hasLetter || !hasDigit) {
                errorsCopy.password = "A jelszónak tartalmaznia kell legalább egy betűt és egy számot!";
                valid = false;
            } else {
                errorsCopy.password = ""; // Clearing error
            }
        }

        setErrors(errorsCopy); // Update errors
        return valid;
    }

    return (
        <div className="container main-content">
            <br/><br/>
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <h2 className="text-center">Bejelentkezés</h2>
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <label className="form-label" htmlFor="email">Email cím:</label>
                                <input type="email"
                                       id="email"
                                       placeholder="Email cím"
                                       name="email"
                                       value={email}
                                       className={`form-control ${errors.email ? "is-invalid" : ""}`}
                                       onChange={(e) => setEmail(e.target.value)}
                                />
                                {errors.email && <div className="invalid-feedback">{errors.email}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label" htmlFor="password">Jelszó:</label>
                                <input type="password"
                                       id="password"
                                       placeholder="Jelszó"
                                       name="password"
                                       value={password}
                                       className={`form-control ${errors.password ? "is-invalid" : ""}`}
                                       onChange={(e) => setPassword(e.target.value)}
                                />
                                {errors.password && <div className="invalid-feedback">{errors.password}</div>}
                            </div>

                            <button className="btn btn-success" onClick={authenticateUser}>Bejelentkezés</button>
                            <br/><br/>
                            <a className="navbar-toggler" href="/UserSignUp">Regisztráció</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserLogInComponent