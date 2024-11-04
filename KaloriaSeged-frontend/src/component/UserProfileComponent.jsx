import React, {useEffect, useState} from "react";
import {createUser, getUserByEmail, getUserById, updateUser} from "../service/UserService.js";
import {useNavigate, useParams} from "react-router-dom";

const UserComponent = () => {

    const [name, setName] = useState([])
    const [email, setEmail] = useState([])
    const [gender, setGender] = useState([])
    const [height, setHeight] = useState([])
    const [weight, setWeight] = useState([])
    const [age, setAge] = useState([])

    const [errors, setErrors] = useState({
        name: "",
        email: "",
        gender: "",
        height: "",
        weight: "",
        age: ""
    })

    const navigator = useNavigate();

    let currentEmail = localStorage.getItem("email")
    console.log(currentEmail)

    useEffect(() =>{
        getUserByEmail(currentEmail).then((response) => {
            setName(response.data.name);
            setEmail(response.data.email);
            setGender(response.data.gender);
            setHeight(response.data.height);
            setWeight(response.data.weight);
            setAge(response.data.age);
        }).catch(error => {
            console.error(error)
        })
    },[])

    function saveOrUpdateUser(e) {
        e.preventDefault();

        if (validateForm()) {

            const user = {name, email}
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
            errorsCopy.name = "Name is required!";
            valid = false;
        }

        if (email.trim) {
            errorsCopy.email = "";
        } else {
            errorsCopy.email = "Email address is required!";
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        return <h2 className="text-center">Profil</h2>
    }

    return (
        <div className="container">
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

                            <button className="btn btn-success mt-3" onClick={saveOrUpdateUser}>Frissít</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserComponent