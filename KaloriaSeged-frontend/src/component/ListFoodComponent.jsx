import React, {useEffect, useState} from 'react'
import {deleteFood, listFoods} from "../service/FoodService.js";
import {useNavigate} from "react-router-dom";

const ListFoodComponent = () => {

    const [foods, setFoods] = useState([])

    const navigator = useNavigate();

    useEffect(() => {
        getAllFoods();
    }, []);

    function getAllFoods() {
        listFoods().then((response) => {
            setFoods(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function addNewFood() {
        navigator("/add-food")
    }

    function updateFood(id) {
        navigator(`/edit-food/${id}`)
    }

    function removeFood(id) {
        console.log(id);

        deleteFood(id).then(() => {
            getAllFoods();
        }).catch(error => {
            console.error(error);
        })
    }

    return (
        <div className="container">
            <h2 className="text-center">List of foods</h2>
            <button className="btn btn-dark mb-2" onClick={addNewFood}>Add Food</button>
            <table className="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Calorie</th>
                    <th>Fat</th>
                    <th>Carbohydrate</th>
                    <th>Protein</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {
                    foods.map(food =>
                        <tr key={food.id}>
                            <td>{food.id}</td>
                            <td>{food.name}</td>
                            <td>{food.calorie}</td>
                            <td>{food.fat}</td>
                            <td>{food.carbohydrate}</td>
                            <td>{food.protein}</td>
                            <td>
                                <button className="btn btn-info" onClick={() => updateFood(food.id)}>Update</button>
                                <button className="btn btn-danger" onClick={() => removeFood(food.id)}
                                style={{marginLeft: "10px"}}>Delete</button>
                            </td>
                        </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default ListFoodComponent
