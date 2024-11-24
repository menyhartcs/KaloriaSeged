import React, {useEffect, useState} from "react";
import {createFood, getFoodById, updateFood} from "../service/FoodService.js";
import {useNavigate, useParams} from "react-router-dom";
import {isNullOrUndef} from "chart.js/helpers";

const FoodComponent = () => {

    const [name, setName] = useState([])
    const [calorie, setCalorie] = useState([])
    const [fat, setFat] = useState([])
    const [carbohydrate, setCarbohydrate] = useState([])
    const [protein, setProtein] = useState([])

    const {id} = useParams();

    useEffect(() => {
        if (isNullOrUndef(localStorage.getItem("email")) && isNullOrUndef(localStorage.getItem("token"))) {
            navigator("/UserLogIn");
        }
    }, []);

    const [errors, setErrors] = useState({
        name: "",
        calorie: "",
        fat: "",
        carbohydrate: "",
        protein: "",
    })

    const navigator = useNavigate();

    useEffect(() =>{
        if (id) {
            getFoodById(id).then((response) => {
                setName(response.data.name);
                setCalorie(response.data.calorie);
                setFat(response.data.fat);
                setCarbohydrate(response.data.carbohydrate);
                setProtein(response.data.protein);
            }).catch(error => {
                console.error(error)
            })
        }

    }, [id])

    function saveOrUpdateFood(e) {
        e.preventDefault();

        if (validateForm()) {

            const food = {name, calorie, fat, carbohydrate, protein}
            console.log(food)

            if (id) {
                updateFood(id, food).then((response) => {
                    console.log(response.data);
                    navigator("/foods");
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createFood(food).then((response) => {
                    console.log(response.data)
                    navigator("/foods")
                }).catch(error => {
                    console.error(error);
                })
            }

        }

    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {... errors}

        if (name.trim()) {
            errorsCopy.name = "";
        } else {
            errorsCopy.name = "Név megadása kötelező!";
            valid = false;
        }

        if (calorie+"".trim()) {
            errorsCopy.calorie = "";
        } else {
            errorsCopy.calorie = "Energia tartalom megadása kötelező!";
            valid = false;
        }

        if (fat+"".trim()) {
            errorsCopy.fat = "";
        } else {
            errorsCopy.fat = "Zsír tartalom megadása kötelező!";
            valid = false;
        }

        if (carbohydrate+"".trim()) {
            errorsCopy.carbohydrate = "";
        } else {
            errorsCopy.carbohydrate = "Szénhidrát tartalom megadása kötelező!";
            valid = false;
        }

        if (protein+"".trim()) {
            errorsCopy.protein = "";
        } else {
            errorsCopy.protein = "Fehérje tartalom megadása kötelező!";
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        if (id) {
            return <h2 className="text-center">Étel szerkesztése</h2>
        } else {
            <h2 className="text-center">Étel hozzáadása</h2>
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
                                       placeholder="Add meg az étel nevét"
                                       name="name"
                                       value={name}
                                       className={`form-control ${errors.name ? "is-invalid" : ""}`}
                                       onChange={(e) => setName(e.target.value)}
                                />
                                {errors.name && <div className="invalid-feedback">{errors.name}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Energia (kcal):</label>
                                <input type="text"
                                       placeholder="Add meg az étel energia tartalmát"
                                       name="calorie"
                                       value={calorie}
                                       className={`form-control ${errors.calorie ? "is-invalid" : ""}`}
                                       onChange={(e) => setCalorie(e.target.value)}
                                />
                                {errors.calorie && <div className="invalid-feedback">{errors.calorie}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Zsír (g):</label>
                                <input type="text"
                                       placeholder="Add meg az étel zsír tartalmát"
                                       name="fat"
                                       value={fat}
                                       className={`form-control ${errors.fat ? "is-invalid" : ""}`}
                                       onChange={(e) => setFat(e.target.value)}
                                />
                                {errors.fat && <div className="invalid-feedback">{errors.fat}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Szénhidrát (g):</label>
                                <input type="text"
                                       placeholder="Add meg az étel szénhidrát tartalmát"
                                       name="carbohydrate"
                                       value={carbohydrate}
                                       className={`form-control ${errors.carbohydrate ? "is-invalid" : ""}`}
                                       onChange={(e) => setCarbohydrate(e.target.value)}
                                />
                                {errors.carbohydrate && <div className="invalid-feedback">{errors.carbohydrate}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Fehérje (g):</label>
                                <input type="text"
                                       placeholder="Add meg az étel fehérje tartalmát"
                                       name="protein"
                                       value={protein}
                                       className={`form-control ${errors.protein ? "is-invalid" : ""}`}
                                       onChange={(e) => setProtein(e.target.value)}
                                />
                                {errors.protein && <div className="invalid-feedback">{errors.protein}</div>}
                            </div>

                            <button className="btn btn-success" onClick={saveOrUpdateFood}>Mentés</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default FoodComponent