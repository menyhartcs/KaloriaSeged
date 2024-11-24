import React, {useEffect, useState} from 'react'
import {deleteUser, listUsers} from "../service/UserService.js";
import {useNavigate} from "react-router-dom";
import {isNullOrUndef} from "chart.js/helpers";

const ListUserComponent = () => {

    const [users, setUsers] = useState([])
    const [filteredUsers, setFilteredUsers] = useState([]);
    const [searchQuery, setSearchQuery] = useState("");
    const navigator = useNavigate();

    useEffect(() => {
        let email = localStorage.getItem("email")
        if (isNullOrUndef(email) && isNullOrUndef(localStorage.getItem("token"))) {
            navigator("/UserLogIn");
        }
        if ("admin@mail.com" === email) {
            getAllUsers();
        } else {
            navigator("/UserFoodLogs")
        }
    }, []);

    useEffect(() => {
        if (isNullOrUndef(localStorage.getItem("email")) && isNullOrUndef(localStorage.getItem("token"))) {
            navigator("/UserLogIn");
        } else {
            getAllUsers();
        }
    }, []);

    function getAllUsers() {
        listUsers().then((response) => {
            setUsers(response.data);
            setFilteredUsers(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function searchUsers(query) {
        setSearchQuery(query);
        if (query === "") {
            setFilteredUsers(users);
        } else {
            const filtered = users.filter(user =>
                user.name.toLowerCase().includes(query.toLowerCase())
            );
            setFilteredUsers(filtered);
        }
    }

    function updateUser(id) {
        navigator(`/edit-user/${id}`)
    }

    function removeUser(id) {
        console.log(id);

        deleteUser(id).then(() => {
            getAllUsers();
        }).catch(error => {
            console.error(error);
        })
    }

    return (
        <div className="container main-content">
            <h2 className="text-center">Felhasználók listája</h2>

            <input
                type="text"
                className="form-control mb-2"
                placeholder="Keresés felhasználó neve alapján"
                value={searchQuery}
                onChange={(e) => searchUsers(e.target.value)}
            />

            <table className="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Név</th>
                    <th>Email cím</th>
                    <th>Műveletek</th>
                </tr>
                </thead>
                <tbody>
                {
                    filteredUsers.map(user =>
                        <tr key={user.id}>
                            <td>{user.id}</td>
                            <td>{user.name}</td>
                            <td>{user.email}</td>
                            <td>
                                <button className="btn btn-warning" onClick={() => updateUser(user.id)}>Szerkesztés</button>
                                <button className="btn btn-danger" onClick={() => removeUser(user.id)}
                                        style={{marginLeft: "10px"}}>Törlés
                                </button>
                            </td>
                        </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default ListUserComponent
