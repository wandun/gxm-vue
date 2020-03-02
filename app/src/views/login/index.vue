<template>
  <div class="login-container" @click.stop="blank">
    <el-form :rules="rules" ref="loginForm"  :model="loginForm" class="login-form" auto-complete="on"
             label-position="left">
      <div>
        <img :src="bear"/>
      </div>
      <h3 class="title">Gxm Blog</h3>
      <el-form-item prop="username">
                <span class="svg-container">
                  <svg-icon icon-class="user"/>
                </span>
        <el-input v-model="loginForm.username" @click.native.stop="greeting" name="username" type="text" auto-complete="on" placeholder="username"/>
      </el-form-item>
      <el-form-item prop="password">
                <span class="svg-container">
                  <svg-icon icon-class="password"/>
                </span>
        <el-input
          @click.native.stop="blindfold"
          :type="pwdType"
          v-model="loginForm.password"
          name="password"
          auto-complete="on"
          placeholder="password"
          @keyup.enter.native="handleLogin"/>
        <span class="show-pwd" @click="showPwd">
                    <svg-icon icon-class="eye"/>
                </span>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loading" type="primary" style="width:100%;" @click.native.prevent="handleLogin('loginForm')">
          Sign in
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

  /*
    1、父组件可以使用 props 把数据传给子组件。
    2、子组件可以使用 $emit 触发父组件的自定义事件
    3、给vue组件绑定事件时候，必须加上native,不然不会生效
  */

  export default {
    name: 'Login',
    data() {
      return {
        img: {
          normal: 'http://cdn.tycoding.cn/normal.0447fe9.png',
          blindfold: 'http://cdn.tycoding.cn/blindfold.58ce423.png',
          greeting: 'http://cdn.tycoding.cn/greeting.1415c1c.png'
        },
        bear: 'http://cdn.tycoding.cn/normal.0447fe9.png',

        loginForm: {
          username: 'gxm',
          password: '123456'
        },
        loading: false,
        pwdType: 'password',
        redirect: undefined,
        /* 卧槽 这里要注意一下，规则名称，要和属性名称一样，
         * 别问我为什么不解释，因为我也不知道，我也是一个彩笔，
         * */
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ]
        }
      }
    },
    watch: {
      $route: {
        handler: function (route) {
          this.redirect = route.query && route.query.redirect
        },
        immediate: true
      }
    },
    methods: {
      blank() {
        this.bear = this.img.normal;
      },
      blindfold() {
        this.bear = this.img.blindfold;
      },
      greeting() {
        this.bear = this.img.greeting;
      },

      showPwd() {
        if (this.pwdType === 'password') {
          this.pwdType = ''
        } else {
          this.pwdType = 'password'
        }
      },
      handleLogin(loginForm) {
        this.$refs[loginForm].validate(valid => {
          if (valid) {
            // 自定义验证通过
            this.loading = true
            this.$store.dispatch('user/login', this.loginForm).then(() => {
              this.loading = false
              this.$router.push({path: '/admin/dashboard'})
            }).catch(() => {
              this.loading = false
            })
          } else {
            this.$message({
              message: '年轻人，不要总是忘东忘西的！',
              type: 'error',
              center: true
            });
            return false
          }
        })
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss">
  $bg: #2d3a4b;
  $light_gray: #eee;

  /* reset element-ui css */
  .login-container {
    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;
      input {
        background: transparent;
        border: 0px;
         -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        height: 47px;
        &:-webkit-autofill {
           -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
           -webkit-text-fill-color: #fff !important;
        }
      }
    }
    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      border-radius: 5px;
      color: #454545;
      .el-form-item__content {
        border-bottom: 1px solid #dcdfe6;
      }
    }
  }

</style>

<style rel="stylesheet/scss" lang="scss" scoped>
  $bg: #2d3a4b;
  $dark_gray: #889aa4;
  $light_gray: #eee;
  .login-container {
    position: fixed;
    height: 100%;
    width: 100%;
    background-color: #b8e5f8;
    background-image: url("http://cdn.tycoding.cn/db29b0fbd2f78dd8c1b7.db29b0f.png");
    .login-form {
      position: absolute;
      left: 0;
      right: 0;
      width: 432px;
      max-width: 100%;
      padding: 35px 35px 15px 35px;
      margin: 120px auto;
      background-color: #fff;

      div>img{
        position: absolute;
        top: 0;
        left: 50%;
        width: 10rem;
         -webkit-transform: translate(-50%, -83%);
        transform: translate(-50%, -83%);
        z-index: 1;
      }
    }
    .tips {
      font-size: 14px;
      margin-bottom: 10px;
      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }
    .svg-container {
      padding: 6px 5px 6px 15px;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
    }
    .title {
      font-size: 26px;
      font-weight: 400;
      color: #0084ff;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
    }
  }
</style>

