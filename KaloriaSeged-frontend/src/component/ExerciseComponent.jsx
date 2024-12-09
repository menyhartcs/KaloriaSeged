import React, {useEffect, useState} from "react";
import {createExercise, getExerciseById, updateExercise} from "../service/ExerciseService.js";
import {useNavigate, useParams} from "react-router-dom";
import {isNullOrUndef} from "chart.js/helpers";
import {checkLoginStatus} from "../service/UserService.js";

const ExerciseComponent = () => {

    const [name, setName] = useState([])
    const [calorie, setCalorie] = useState([])

    const {id} = useParams();

    useEffect(() => {
        checkLoginStatus(localStorage.getItem("email")).then(() => {
        }).catch(() => {
            console.log("ISMERETLEN FELHASZNÁLÓ")
            navigator("/UserLogIn");
        })
    }, []);

    const [errors, setErrors] = useState({
        name: "",
        calorie: ""
    })

    const navigator = useNavigate();

    useEffect(() =>{
        if (id) {
            getExerciseById(id).then((response) => {
                setName(response.data.name);
                setCalorie(response.data.calorie);
            }).catch(error => {
                console.error(error)
            })
        }

    }, [id])

    function saveOrUpdateExercise(e) {
        e.preventDefault();

        if (validateForm()) {

            const exercise = {name, calorie}
            console.log(exercise)

            if (id) {
                updateExercise(id, exercise).then((response) => {
                    console.log(response.data);
                    navigator("/exercises");
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createExercise(exercise).then((response) => {
                    console.log(response.data)
                    navigator("/exercises")
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

        if (calorie+"".trim()) {
            errorsCopy.calorie = "";
        } else {
            errorsCopy.calorie = "Tevékenységgel elégethető energia megadása kötelező!";
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        if (id) {
            return <h2 className="text-center">Tevékenység szerkesztése</h2>
        } else {
            return <h2 className="text-center">Tevékenység hozzáadása</h2>
        }
    }

    return (
        <div className="container main-content">
            <br/><br/>
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    {
                        pageTitle()
                    }
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <label className="form-label">Név:</label>
                                <input type="text"
                                       placeholder="Add meg a tevékenység nevét"
                                       name="name"
                                       value={name}
                                       className={`form-control ${errors.name ? "is-invalid" : ""}`}
                                       onChange={(e) => setName(e.target.value)}
                                />
                                {errors.name && <div className="invalid-feedback">{errors.name}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Energia (kcal):</label>
                                <input type="number"
                                       placeholder="Add meg mennyi energiát éget el a tevékenység"
                                       name="calorie"
                                       value={calorie}
                                       className={`form-control ${errors.calorie ? "is-invalid" : ""}`}
                                       onChange={(e) => setCalorie(e.target.value)}
                                />
                                {errors.calorie && <div className="invalid-feedback">{errors.calorie}</div>}
                            </div>

                            <button className="btn btn-success" onClick={saveOrUpdateExercise}>Mentés</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ExerciseComponent