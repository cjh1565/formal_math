async function getList({pno, page, size}){
    const result = await axios.get(`/replies/list/${pno}`, {params: {page, size}})
    return result.data
}

async function addReply(replyObj) {
    const response = await axios.post(`/replies/`,replyObj)
    return response.data
}

async function getReply(rno) {
    const response = await axios.get(`/replies/${rno}`)
    return response.data
}

async function modifyReply(replyObj) {
    const response = await axios.put(`/replies/${replyObj.rno}`, replyObj)
    return response.data
}

async function removeReply(rno) {
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}
