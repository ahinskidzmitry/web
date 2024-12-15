function validateTitle(topic) {
    const regex = /^.*.{10,100}$/;
    if(regex.test(topic) === false) {
        return false;
    }
    return true;
}
function validateText(text) {
    const regex = /^.*.{10,1000}$/;
    if(regex.test(text) === false) {
        return false;
    }
    return true;
}

function validateArticleFields(topic, text) {
    const error = document.getElementsByClassName("error");
    const topicTitle = document.getElementsByName("topic").item(0).getAttribute("title");
    const textTitle = document.getElementsByName("text").item(0).getAttribute("title");
    if(validateTitle(topic) === false) {
        error.item(0).innerHTML = topicTitle;
        return false;
    } else if (validateText(text) === false) {
        error.item(0).innerHTML = textTitle;
        return false;
    }
    return true;
}

function validateComment(text) {
    const error = document.getElementsByClassName("error");
    const commentTitle = document.getElementsByName("comment_text").item(0).getAttribute("title");
    const regex = /^.*.{10,1000}$/;
    if(regex.test(text) === false) {
        error.item(0).innerHTML = commentTitle;
        return false;
    }
    return true;
}
