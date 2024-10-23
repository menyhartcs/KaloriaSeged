import React, {useState} from "react";
import {getUserByEmail, loginUser} from "../service/UserService.js";
import {useNavigate} from "react-router-dom";
import moment from "moment/moment.js";

const UserLogInComponent = () => {

    const [name, setName] = useState([]);
    const [id, setId] = useState([]);
    const [email, setEmail] = useState([]);
    const [password, setPassword] = useState([]);
    const [selectedDate, setSelectedDate] = useState(getCurrentDate);

    const [errors, setErrors] = useState({
        id: "",
        name: "",
        email: "",
        password: ""
    })

    const navigator = useNavigate();

    async function fetchUserDataByEmail(email) {
        try {
            const response = await getUserByEmail(email);
            return response.data;
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    function getCurrentDate() {
        return moment().format('YYYY-MM-DD');
    }

    async function authenticateUser(e) {
        e.preventDefault();

        if (!validateForm()) return;

        try {

            // const userData = await fetchUserDataByEmail(email);

            const user = {
                email: email,
                password: password
            };

            const response = await loginUser(user);
            const token = response.data.token;

            localStorage.setItem("token", token);

            console.log("Successful login");
            navigator(`/userFoodLog/searchByUserIdAndDate?userId=${user.id}&date=${selectedDate}`);
        } catch (error) {
            console.error("Login failed", error);
        }
    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {... errors}

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

    function pageTitle() {
        if (id) {
            return <h2 className="text-center">Update User</h2>
        } else {
            <h2 className="text-center">Bejelentkezés</h2>
        }
    }

    return (
        <div className="container">
            <br/><br/>
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    {
                        pageTitle()
                    }
                    <div className="card-body">
                        <form>
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