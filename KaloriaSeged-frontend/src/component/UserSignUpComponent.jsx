import React, {useEffect, useState} from "react";
import {createUser} from "../service/UserService.js";
import {useNavigate, useParams} from "react-router-dom";
import {isNullOrUndef} from "chart.js/helpers";

const UserSignUpComponent = () => {

    const [name, setName] = useState([])
    const [email, setEmail] = useState([])
    const [password, setPassword] = useState([])

    useEffect(() => {
        if (!isNullOrUndef(localStorage.getItem("email")) && !isNullOrUndef(localStorage.getItem("token"))) {
            navigator("/UserFoodLogs");
        }
    }, []);

    const [errors, setErrors] = useState({
        name: "",
        email: "",
        password: ""
    })

    const navigator = useNavigate();

    function registerUser(e) {
        e.preventDefault();

        if (validateForm()) {

            const user = {name, email, password}
            console.log(user)
            createUser(user).then((response) => {
                console.log(response.data)
                navigator("/users")
            }).catch(error => {
                if (error.response && error.response.data) {
                    // Backend error message handling
                    const backendError = error.response.data;
                    setErrors((prevErrors) => ({ ...prevErrors, email: backendError }));
                } else {
                    console.error("Ismeretlen hiba történt!", error);
                }
            })

        }

    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {...errors}; // Create copy of errors

        if (!name.trim()) {
            errorsCopy.name = "Név megadása kötelező!";
            valid = false;
        } else {
            errorsCopy.name = ""; // Clearing error
        }

        if (!email.trim()) {
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

        if (!password.trim()) {
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
                    <h2 className="text-center">Regisztráció</h2>
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <label className="form-label">Név:</label>
                                <input type="text"
                                       placeholder="Név"
                                       name="name"
                                       value={name}
                                       className={`form-control ${errors.name ? "is-invalid" : ""}`}
                                       onChange={(e) => setName(e.target.value)}
                                />
                                {errors.name && <div className="invalid-feedback">{errors.name}</div>}
                            </div>

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
                                <label className="form-label">Jelszó:</label>
                                <input type="password"
                                       placeholder="Jelszó"
                                       name="password"
                                       value={password}
                                       className={`form-control ${errors.password ? "is-invalid" : ""}`}
                                       onChange={(e) => setPassword(e.target.value)}
                                />
                                {errors.password && <div className="invalid-feedback">{errors.password}</div>}
                            </div>

                            <button className="btn btn-success" onClick={registerUser}>Regisztrálás</button>
                            <br/><br/>
                            <a className="navbar-toggler" href="/UserLogIn">Bejelentkezés</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserSignUpComponent