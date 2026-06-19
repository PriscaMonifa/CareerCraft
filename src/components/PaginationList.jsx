import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getAllData } from "../store/action/characterAction";


function Pagination() {

    const dispatch = useDispatch();
    const { characters, pages } = useSelector(state => state.character);
    const [currentPage, setCurrentPage] = useState(1);
    let count = 0;

    useEffect(() => {
        dispatch(getAllData(currentPage));
    }, [currentPage, dispatch]);

    return (
        <div>
            <h1>List Of Characters</h1>
            <table className="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Species</th>
                        <th>Origin name</th>
                        <th>Location name</th>
                    </tr>
                </thead>

                <tbody>
                    {
                        characters.map((c) => (
                            <tr key={c.id}>
                                <td>{c.name}</td>
                                <td>{c.status}</td>
                                <td>{c.species}</td>
                                <td>{c.origin.name}</td>
                                <td>{c.location.name}</td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>

            <nav aria-label="Page navigation example">
                <ul className="pagination justify-content-center">

                    <li className="page-item">
                        <button
                            className="page-link"
                            disabled={currentPage === 1}
                            onClick={() => setCurrentPage(currentPage - 1)}
                        >
                            Previous
                        </button>
                    </li>

                    {
                        Array.from({ length: pages }).map((_, index) => (
                            <li
                                className="page-item"
                                key={index + 1}
                            >
                                <button
                                    className="page-link"
                                    onClick={() => setCurrentPage(index + 1)}
                                >
                                    {count = count + 1}
                                </button>
                            </li>
                        ))
                    }

                    <li className="page-item">
                        <button
                            className="page-link"
                            disabled={currentPage === pages}
                            onClick={() => setCurrentPage(currentPage + 1)}
                        >
                            Next
                        </button>
                    </li>

                </ul>
            </nav>
        </div>
    );
}

export default Pagination;