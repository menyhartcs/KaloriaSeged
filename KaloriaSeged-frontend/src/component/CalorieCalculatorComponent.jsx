import React, {useEffect, useState} from "react";
import {createUser, getUserByEmail, getUserById, updateUser} from "../service/UserService.js";
import {useNavigate} from "react-router-dom";

const CalorieCalculatorComponent = () => {

    const [id, setId] = useState([])
    const [name, setName] = useState([])
    const [password, setPassword] = useState([])
    const [email, setEmail] = useState([])
    const [gender, setGender] = useState([])
    const [height, setHeight] = useState([])
    const [weight, setWeight] = useState([])
    const [age, setAge] = useState([])
    const [activity, setActivity] = useState([])

    const [errors, setErrors] = useState({
        id: "",
        name: "",
        password: "",
        email: "",
        gender: "",
        height: "",
        weight: "",
        age: ""
    })

    const navigator = useNavigate();

    let currentEmail = localStorage.getItem("email")

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
        }).catch(error => {
            console.error(error)
        })
    },[])

    function calculate(e) {
        e.preventDefault();

        let bmr;
        if (validateForm()) {



            if (gender === "M") {
                bmr = Math.round((10 * weight + 6.25 * height - 5 * age + 5) * activity)
            }

            if (gender === "F") {
                bmr = Math.round(10 * weight + 6.25 * height - 5 * age - 161)
            }

            console.log("BMR: " + bmr)

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
        return <h2 className="text-center">Kalória kalkulátor</h2>
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
                        <ul>
                            <li>ülő típus – minimális vagy semmilyen mozgás esetén, a BMR-t 1,2 koefficienssel szorozd
                                meg
                            </li>
                            <li>
                                enyhén aktív típus – ha hetente 1-3 alkalommal sportolsz, a BMR-t szorozd meg 1,375-tel
                            </li>
                            <li>
                                közepesen aktív típus – ha heti 3-5-ször sportolsz, a BMR-t 1,55
                                koefficienssel szorozd meg
                            </li>
                            <li>
                                nagyon aktív típus – ha rendszeresen sportolsz a hét 6-7 napján, a BMR-t
                                1,725 koefficienssel szorozd meg
                            </li>
                            <li>
                                extra aktív típus – ha professzionális sportoló vagy és rendszeresen
                                6-7 alkalommal edzel vagy nehéz fizikai munkát végzel, a BMR-t 1,9
                                szorozd meg
                            </li>
                        </ul>
                        <form>
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

                                <label className="form-label ms-3 me-2">Napi aktivitás:</label>
                                <select name="activity" id="activity"
                                        value={activity}
                                        onChange={(e) => setActivity(e.target.value)}>
                                    <option value="1.2">Mozgásszegény életmód</option>
                                    <option value="1.375">Könnyű aktivitás</option>
                                    <option value="1.55">Mérsékelt aktivitás</option>
                                    <option value="1.725">Nagyon aktív</option>
                                    <option value="1.9">Extra aktív</option>
                                </select>
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

                            <button className="btn btn-success mt-3" onClick={calculate}>Számít</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default CalorieCalculatorComponent