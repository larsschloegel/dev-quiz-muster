import {useContext, useState} from "react";
import {AuthContext} from "../context/AuthProvider";

const initialState = {
    username: "",
    password: "",
}
export default function LoginPage() {
    const [credentials, setCredentials] = useState(initialState);
    const {login} = useContext(AuthContext)
    const client_id = "d29a927b3859396a4ad4";

    const handleChange = event => {
        setCredentials({...credentials, [event.target.name]: event.target.value})
    }

    const handleSubmit = event => {
        event.preventDefault()
        login(credentials)
    }

    const loginWithGithub = () => {
        window.open(`https://github.com/login/oauth/authorize?client_id=${client_id}`)
    }

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    <input
                        type="text"
                        placeholder="username"
                        required
                        name="username"
                        onChange={handleChange}
                        value={credentials.username}
                    />
                </label>
                <label>
                    <input
                        type="password"
                        required
                        placeholder="password"
                        name="password"
                        onChange={handleChange}
                        value={credentials.password}
                    />
                </label>
                <button>Login</button>
            </form>

            <button type={"button"}
                    onClick={loginWithGithub}>LoginWithGithub
            </button>


        </div>
    )
}