import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthProvider";


export default function GitHubRedirectPage(){

    const [data, setData] = useState({errorMessage: "", isLoading: false});
    const {loginWithGithub} = useContext(AuthContext)

    useEffect(() => {
        const url = window.location.href;
        const hasCode = url.includes("?code=");

        // If Github API returns the code parameter
        if (hasCode) {
            const newUrl = url.split("?code=");
            window.history.pushState({}, null, newUrl[0]);
            setData({...data, isLoading: true});
            const requestData = {
                code: newUrl[1]
            };
            loginWithGithub(requestData.code)
        }
    }, [data]);


    return(
        <p>Logging in...</p>
    )
}