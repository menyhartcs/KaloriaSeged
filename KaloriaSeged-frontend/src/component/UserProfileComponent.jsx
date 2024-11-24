import React, {useEffect, useState} from "react";
import {getUserByEmail, updateUser} from "../service/UserService.js";
import {useNavigate} from "react-router-dom";
import {isNullOrUndef} from "chart.js/helpers";

const UserProfileComponent = () => {

    const [id, setId] = useState([])
    const [name, setName] = useState([])
    const [password, setPassword] = useState([])
    const [email, setEmail] = useState([])
    const [gender, setGender] = useState([])
    const [height, setHeight] = useState([])
    const [weight, setWeight] = useState([])
    const [age, setAge] = useState([])
    const [userCalorie, setUserCalorie] = useState([])
    const [userProtein, setUserProtein] = useState([])
    const [userCarbohydrate, setUserCarbohydrate] = useState([])
    const [userFat, setUserFat] = useState([])

    useEffect(() => {
        if (isNullOrUndef(localStorage.getItem("email")) && isNullOrUndef(localStorage.getItem("token"))) {
            navigator("/UserLogIn");
        }
    }, []);

    const [errors, setErrors] = useState({
        id: "",
        name: "",
        password: "",
        email: "",
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

    let currentEmail = localStorage.getItem("email")
    console.log(currentEmail)

    useEffect(() =>{
        getUserByEmail(currentEmail).then((response) => {
            setId(response.data.id);
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
    },[])

    function saveOrUpdateUser(e) {
        e.preventDefault();

        if (validateForm()) {

            const user = {
                id,
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

            updateUser(id, user).then((response) => {
                console.log(response.data);
                navigator("/UserProfile");
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

        if (height+"".trim()) {
            errorsCopy.height = "";
        } else {
            errorsCopy.height = "Magasság megadása kötelező!";
            valid = false;
        }

        if (weight+"".trim()) {
            errorsCopy.weight = "";
        } else {
            errorsCopy.weight = "Súly megadása kötelező!";
            valid = false;
        }

        if (age+"".trim()) {
            errorsCopy.age = "";
        } else {
            errorsCopy.age = "Kor megadása kötelező!";
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        return <h2 className="text-center">Profil</h2>
    }

    return (
        <div className="container main-content">
            <br/><br/>
            <div className="row">
                <div className="card col-md-6 offset-md-3">
                    {
                        pageTitle()
                    }
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <label className="form-label">Név:</label>
                                <input type="text"
                                       placeholder="Enter username"
                                       name="name"
                                       value={name}
                                       className={`form-control ${errors.name ? "is-invalid" : ""}`}
                                       onChange={(e) => setName(e.target.value)}
                                />
                                {errors.name && <div className="invalid-feedback">{errors.name}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Email:</label>
                                <input type="text"
                                       placeholder="Enter email address"
                                       name="email"
                                       value={email}
                                       className={`form-control ${errors.email ? "is-invalid" : ""}`}
                                       onChange={(e) => setEmail(e.target.value)}
                                />
                                {errors.email && <div className="invalid-feedback">{errors.email}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label me-2">Férfi:</label>
                                <input type="radio"
                                       name="gender"
                                       value="M"
                                       checked={gender === "M"}
                                       onChange={(e) => setGender(e.target.value)}
                                />
                                <label className="form-label ms-3 me-2">Nő:</label>
                                <input type="radio"
                                       name="gender"
                                       value="F"
                                       checked={gender === "F"}
                                       onChange={(e) => setGender(e.target.value)}
                                />
                                {errors.gender && <div className="invalid-feedback">{errors.gender}</div>}
                            </div>

                            <div className="form-group col-md-4 mb-2">
                            <label className="form-label">Magasság(cm):</label>
                                <input type="number"
                                       name="height"
                                       value={height}
                                       className={`form-control ${errors.height ? "is-invalid" : ""}`}
                                       onChange={(e) => setHeight(e.target.value)}
                                />
                                {errors.height && <div className="invalid-feedback">{errors.height}</div>}
                            </div>

                            <div className="form-group col-md-4 mb-2">
                                <label className="form-label">Testsúly(kg):</label>
                                <input type="number"
                                       name="weight"
                                       value={weight}
                                       className={`form-control ${errors.weight ? "is-invalid" : ""}`}
                                       onChange={(e) => setWeight(e.target.value)}
                                />
                                {errors.weight && <div className="invalid-feedback">{errors.weight}</div>}
                            </div>

                            <div className="form-group col-md-4 mb-2">
                                <label className="form-label">Kor:</label>
                                <input type="number"
                                       name="age"
                                       value={age}
                                       className={`form-control ${errors.age ? "is-invalid" : ""}`}
                                       onChange={(e) => setAge(e.target.value)}
                                />
                                {errors.age && <div className="invalid-feedback">{errors.age}</div>}
                            </div>

                            <button className="btn btn-primary mt-3" onClick={saveOrUpdateUser}>Frissít</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserProfileComponent