import React, {useEffect, useState} from "react";
import {createUser, getUserById, updateUser} from "../service/UserService.js";
import {useNavigate, useParams} from "react-router-dom";
import {isNullOrUndef} from "chart.js/helpers";

const UserComponent = () => {

    const [name, setName] = useState([])
    const [email, setEmail] = useState([])
    const [password, setPassword] = useState([])
    const [gender, setGender] = useState([])
    const [height, setHeight] = useState([])
    const [weight, setWeight] = useState([])
    const [age, setAge] = useState([])
    const [userCalorie, setUserCalorie] = useState([])
    const [userProtein, setUserProtein] = useState([])
    const [userCarbohydrate, setUserCarbohydrate] = useState([])
    const [userFat, setUserFat] = useState([])

    const {id} = useParams();

    useEffect(() => {
        let email = localStorage.getItem("email")
        if (isNullOrUndef(email) && isNullOrUndef(localStorage.getItem("token"))) {
            navigator("/UserLogIn");
        }
        if ("admin@mail.com" !== email) {
            navigator("/UserFoodLogs")
        }
    }, []);

    const [errors, setErrors] = useState({
        name: "",
        email: "",
        password: "",
        gender: "",
        height: "",
        weight: "",
        age: "",
        userCalorie: "",
        userProtein: "",
        userCarbohydrate: "",
        userFat: ""
    })

    const navigator = useNavigate();

    useEffect(() =>{
        if (id) {
            getUserById(id).then((response) => {
                setName(response.data.name);
                setEmail(response.data.email);
                setPassword(response.data.password);
                setGender(response.data.gender);
                setHeight(response.data.height);
                setWeight(response.data.weight);
                setAge(response.data.age);
                setUserCalorie(response.data.userCalorie);
                setUserProtein(response.data.userProtein);
                setUserCarbohydrate(response.data.userCarbohydrate);
                setUserFat(response.data.userFat);
                console.log(response.data)
            }).catch(error => {
                console.error(error)
            })
        }

    }, [id])

    function saveOrUpdateUser(e) {
        e.preventDefault();

        if (validateForm()) {

            const user = {
                name,
                password,
                email,
                gender,
                height,
                weight,
                age,
                userCalorie,
                userProtein,
                userCarbohydrate,
                userFat
            }
            console.log(user)

            if (id) {
                updateUser(id, user).then((response) => {
                    console.log(response.data);
                    navigator("/users");
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createUser(user).then((response) => {
                    console.log(response.data)
                    navigator("/users")
                }).catch(error => {
                    console.error(error);
                })
            }

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
            const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            if (!emailRegex.test(email)) {
                errorsCopy.email = "Az email cím formátuma érvénytelen!";
                valid = false;
            } else {
                errorsCopy.email = "";
            }
        } else {
            errorsCopy.email = "Email cím megadása kötelező!";
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
                    <h2 className="text-center">Felhasználó szerkesztése</h2>
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <label className="form-label">Név:</label>
                                <input type="text"
                                       placeholder="Add meg a nevet"
                                       name="name"
                                       value={name}
                                       className={`form-control ${errors.name ? "is-invalid" : ""}`}
                                       onChange={(e) => setName(e.target.value)}
                                />
                                { errors.name && <div className="invalid-feedback">{ errors.name }</div> }
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Email cím:</label>
                                <input type="text"
                                       placeholder="Add meg az email címet"
                                       name="email"
                                       value={email}
                                       className={`form-control ${errors.email ? "is-invalid" : ""}`}
                                       onChange={(e) => setEmail(e.target.value)}
                                />
                                { errors.email && <div className="invalid-feedback">{ errors.email }</div> }
                            </div>

                            <button className="btn btn-success" onClick={saveOrUpdateUser}>Mentés</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserComponent