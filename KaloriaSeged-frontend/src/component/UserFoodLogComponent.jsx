import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {createUserFoodLog, getUserFoodLog, updateUserFoodLog} from "../service/UserFoodLogService.js";

const UserFoodLogComponent = () => {

    const [User, setUser] = useState([])
    const [Food, setFood] = useState([])
    const [date, setDate] = useState([])

    const {id} = useParams();

    const [errors, setErrors] = useState({
        User: "",
        Food: "",
        date: "",
    })

    const navigator = useNavigate();

    useEffect(() =>{
        if (id) {
            getUserFoodLog(id).then((response) => {
                setUser(response.data.User);
                setFood(response.data.Food);
                setDate(response.data.date);
            }).catch(error => {
                console.error(error)
            })
        }

    }, [id])

    function saveOrUpdateUserFoodLog(e) {
        e.preventDefault();

        if (validateForm()) {

            const userFoodLog = {User, Food, date}
            console.log(userFoodLog)

            if (id) {
                updateUserFoodLog(id, userFoodLog).then((response) => {
                    console.log(response.data);
                    navigator("/UserFoodLogs");
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createUserFoodLog(userFoodLog).then((response) => {
                    console.log(response.data)
                    navigator("/UserFoodLogs")
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

        if (calorie.trim) {
            errorsCopy.calorie = "";
        } else {
            errorsCopy.calorie = "Calorie is required!";
            valid = false;
        }

        if (calorie.trim) {
            errorsCopy.fat = "";
        } else {
            errorsCopy.fat = "Fat is required!";
            valid = false;
        }

        if (calorie.trim) {
            errorsCopy.carbohydrate = "";
        } else {
            errorsCopy.carbohydrate = "Carbohydrate is required!";
            valid = false;
        }

        if (calorie.trim) {
            errorsCopy.protein = "";
        } else {
            errorsCopy.protein = "Protein is required!";
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        if (id) {
            return <h2 className="text-center">Update Food</h2>
        } else {
            <h2 className="text-center">Add Food</h2>
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
                                <label className="form-label">Name:</label>
                                <input type="text"
                                       placeholder="Enter food name"
                                       name="name"
                                       value={name}
                                       className={`form-control ${errors.name ? "is-invalid" : ""}`}
                                       onChange={(e) => setName(e.target.value)}
                                />
                                {errors.name && <div className="invalid-feedback">{errors.name}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Calorie:</label>
                                <input type="text"
                                       placeholder="Enter calorie"
                                       name="calorie"
                                       value={calorie}
                                       className={`form-control ${errors.calorie ? "is-invalid" : ""}`}
                                       onChange={(e) => setCalorie(e.target.value)}
                                />
                                {errors.calorie && <div className="invalid-feedback">{errors.calorie}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Fat:</label>
                                <input type="text"
                                       placeholder="Enter fat"
                                       name="fat"
                                       value={fat}
                                       className={`form-control ${errors.fat ? "is-invalid" : ""}`}
                                       onChange={(e) => setFat(e.target.value)}
                                />
                                {errors.fat && <div className="invalid-feedback">{errors.fat}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Carbohydrate:</label>
                                <input type="text"
                                       placeholder="Enter carbohydrate"
                                       name="carbohydrate"
                                       value={carbohydrate}
                                       className={`form-control ${errors.carbohydrate ? "is-invalid" : ""}`}
                                       onChange={(e) => setCarbohydrate(e.target.value)}
                                />
                                {errors.carbohydrate && <div className="invalid-feedback">{errors.carbohydrate}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Protein:</label>
                                <input type="text"
                                       placeholder="Enter protein"
                                       name="protein"
                                       value={protein}
                                       className={`form-control ${errors.protein ? "is-invalid" : ""}`}
                                       onChange={(e) => setProtein(e.target.value)}
                                />
                                {errors.protein && <div className="invalid-feedback">{errors.protein}</div>}
                            </div>

                            <button className="btn btn-success" onClick={saveOrUpdateFood}>Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserFoodLogComponent