import React, {useEffect, useState} from 'react'
import {deleteFood, listFoods} from "../service/FoodService.js";
import {useNavigate} from "react-router-dom";

const ListFoodComponent = () => {

    const [foods, setFoods] = useState([])
    const [filteredFoods, setFilteredFoods] = useState([]);
    const [searchQuery, setSearchQuery] = useState("");
    const navigator = useNavigate();

    useEffect(() => {
        getAllFoods();
    }, []);


    function getAllFoods() {
        listFoods().then((response) => {
            setFoods(response.data);
            setFilteredFoods(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function searchFoods(query) {
        setSearchQuery(query);
        if (query === "") {
            setFilteredFoods(foods);
        } else {
            const filtered = foods.filter(food =>
                food.name.toLowerCase().includes(query.toLowerCase())
            );
            setFilteredFoods(filtered);
        }
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
        <div className="container main-content">
            <h2 className="text-center">Elérhető ételek listája</h2>

            <input
                type="text"
                className="form-control mb-2"
                placeholder="Keresés étel neve alapján"
                value={searchQuery}
                onChange={(e) => searchFoods(e.target.value)}
            />

            <button className="btn btn-dark mb-2" onClick={addNewFood}>Étel hozzáadása</button>
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
                    filteredFoods.map(food =>
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
                                        style={{marginLeft: "10px"}}>Delete
                                </button>
                            </td>
                        </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default ListFoodComponent
