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
    const [activity, setActivity] = useState()
    const [result, setResult] = useState()

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

        if (validateForm()) {
            let bmr;
            let activityMultiplier;
            let calorie;
            let protein;
            let carbohydrate;
            let fat;
            switch (activity) {
                case "1":
                    activityMultiplier = 1.2;
                    break;
                case "2":
                    activityMultiplier=1.375;
                    break;
                case "3":
                    activityMultiplier=1.55;
                    break;
                case "4":
                    activityMultiplier=1.725;
                    break;
                case "5":
                    activityMultiplier=1.9;
                    break;
                default:
                    activityMultiplier=1.2
            }
            if (gender === "M") {
                bmr = 10 * weight + 6.25 * height - 5 * age + 5
                calorie = Math.round(bmr * activityMultiplier)
                protein = Math.round(1.6 * weight)
                fat = Math.round(0.25 * calorie / 9)
                carbohydrate = Math.round((calorie - protein * 4 - fat * 9) / 4)
            }

            if (gender === "F") {
                bmr = Math.round((10 * weight + 6.25 * height - 5 * age - 161) * activityMultiplier)
            }

            setResult(`
            <table style="border: 1px solid black; border-collapse: collapse; width: 100%;">
                <tr><th>Kalória</th><td>${calorie} kcal</td></tr>
                <tr><th>Fehérje</th><td>${protein} g</td></tr>
                <tr><th>Szénhidrát</th><td>${carbohydrate} g</td></tr>
                <tr><th>Zsír</th><td>${fat} g</td></tr>
            </table>
        `);
            console.log("BMR: " + bmr)
            console.log("calorie: " + calorie)
            console.log("protein: " + protein)
            console.log("fat: " + fat)
            console.log("carbohydrate: " + carbohydrate)

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
                            <li>Ülő életmód – minimális vagy semmilyen mozgás esetén
                            </li>
                            <li>
                                Enyhén aktív – ha hetente 1-3 alkalommal sportolsz
                            </li>
                            <li>
                                Közepesen aktív – ha heti 3-5-ször sportolsz
                            </li>
                            <li>
                                Nagyon aktív – ha rendszeresen sportolsz a hét 6-7 napján
                            </li>
                            <li>
                                Extra aktív típus – ha professzionális sportoló vagy és rendszeresen
                                6-7 alkalommal edzel vagy nehéz fizikai munkát végzel
                            </li>
                        </ul>
                        <div className="row">
                            <div className="col-md-6">
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
                                                defaultValue="1.2"
                                                value={activity}
                                                onChange={(e) => setActivity(e.target.value)}>
                                            <option value="1">Ülő életmód</option>
                                            <option value="2">Enyhén aktív</option>
                                            <option value="3">Közepesen aktív</option>
                                            <option value="4">Nagyon aktív</option>
                                            <option value="5">Extra aktív</option>
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
                            <div className="col-md-6">
                                <h5>Eredmény:</h5>
                                <div dangerouslySetInnerHTML={{__html: result}}></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default CalorieCalculatorComponent