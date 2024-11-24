import React, {useState} from "react";
import {createUser} from "../service/UserService.js";
import {useNavigate, useParams} from "react-router-dom";

const UserSignUpComponent = () => {

    const [name, setName] = useState([])
    const [email, setEmail] = useState([])
    const [password, setPassword] = useState([])

    const {id} = useParams();

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
                console.error(error);
            })

        }

    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {... errors}

        if (name.trim) {
            errorsCopy.name = "";
        } else {
            errorsCopy.name = "Név megadása kötelező!";
            valid = false;
        }

        if (email.trim) {
            errorsCopy.email = "";
        } else {
            errorsCopy.email = "Email cím megadása kötelező!";
            valid = false;
        }

        if (password.trim) {
            errorsCopy.password = "";
        } else {
            errorsCopy.password = "Jelszó megadása kötelező!";
            valid = false;
        }

        setErrors(errorsCopy);

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
                                <label className="form-label">Email cím:</label>
                                <input type="text"
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