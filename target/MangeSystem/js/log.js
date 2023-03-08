//获取登录状态
function getUserInfo(pageName) {
  $.ajax({
    type: "GET",
    url: "/MangeSystem/log",
    success: function (body) {
      //判断body是否合法
      if (body.id && body.id > 0) {
        //登录成功
        console.log("用户已经成功 用户名:" + body.name);
        //将用户名设置到对应位置
        if (pageName == "index.html") {
          changeUserMsg(body.name);
        }
      } else {
        //跳转到登录页面
        alert("请登录后访问");
        location.assign("/MangeSystem/log.html");
      }
    },
    error: function () {
      //跳转到登录页面
      location.assign("/ManegeSystem/log.html");
    },
  });
}

//更改页面信息
function changeUserMsg(userName) {
  //根据获取到的信息修改用户信息
  let user = document.querySelector(".card>h3");
  user.innerHTML = userName;
}
