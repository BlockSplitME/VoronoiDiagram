async function initField(count, width, height) {
    let params = new URLSearchParams(); 
    params.set('count', count);
    params.set('width', width);
    params.set('height', height);

    return baseRequest("initField?" + params)
}
async function doStep() {
    return baseRequest("nextStep?")
}
async function getResult() {
    return baseRequest("result?")
}
async function baseRequest(params) {
    return fetch(`http://localhost:${Number(process.env.BACK_PORT ?? 8083)}/api/` + params, {
       method: 'GET',
    }).then(function(response){
        console.log("Data received");
        return response.json();
    })
}

export default { 
    initField,
    doStep,
    getResult
}