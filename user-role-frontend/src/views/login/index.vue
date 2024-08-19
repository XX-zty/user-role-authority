<template>
  <div class="login_container">
    <div class="login_box">
      <!-- 登录标识 -->
      <div class="login_title">
        <i class="el-icon-lock"></i>
        <span>登录</span>
      </div>
      <!-- 登录表单区域 -->
      <el-form
        ref="loginFormRef"
        label-width="0px"
        class="login_form"
        :model="loginForm"
        :rules="loginFormRules"
      >
        <!-- 用户名区域 -->
        <el-form-item prop="username">
          <el-input
            prefix-icon="el-icon-user"
            v-model="loginForm.username"
            type="text"
            @keyup.enter.native="login"
            placeholder="请输入用户名"
          ></el-input>
        </el-form-item>
        <!-- 密码区域 -->
        <el-form-item prop="password">
          <el-input
            prefix-icon="el-icon-lock"
            v-model="loginForm.password"
            type="password"
            @keyup.enter.native="login"
            placeholder="请输入密码"
          ></el-input>
        </el-form-item>
        <!-- 按钮区域 -->
        <el-form-item class="btns">
          <el-button type="primary" @click="login">登录</el-button>
          <!-- <el-button type="primary" @click="register">注册</el-button> -->
          <el-button type="info" @click="resetLoginForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>



<script>
import { login } from '../../api/user'
import { setToken } from '../../utils/auth'

export default {
  name: 'index',
  mounted() {
    document.title = '登录';
  },
  data() {
    return {
      // 登录表单的数据绑定对象
      loginForm: {},
      // 表单的验证规则对象
      loginFormRules: {
        // 验证用户名是否合法
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 5,
            max: 15,
            message: '长度在 5 到 15 个字符',
            trigger: 'blur'
          }
        ],
        // 验证密码是否合法
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            min: 5,
            max: 15,
            message: '长度在 5 到 15 个字符',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    resetLoginForm() {
      this.$refs['loginFormRef'].resetFields()
    },
    login() {
      this.$refs.loginFormRef.validate(async (valid) => {
        if (!valid) return
        login(this.loginForm).then((res) => {
          setToken(res.data.Authorization)
          this.$router.push('/layout')
          this.$message.success('登录成功')
        })
      })
    },
    register() {
      this.$router.push('/register')
    }
  }
}
</script>

<style lang="less" scoped>
.login_container {
  background: linear-gradient(135deg, #89f7fe 0%, #66a6ff 100%);
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  animation: gradientAnimation 5s infinite alternate;

  @keyframes gradientAnimation {
    0% {
      background: linear-gradient(135deg, #89f7fe 0%, #66a6ff 100%);
    }
    100% {
      background: linear-gradient(135deg, #66a6ff 0%, #89f7fe 100%);
    }
  }
}

.login_box {
  width: 450px;
  padding: 40px 30px;
  background-color: rgba(255, 255, 255, 0.9); /* 白色带透明度 */
  border-radius: 15px;
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2);
  position: relative;
}

.login_title {
  text-align: center;
  font-size: 28px;
  font-weight: 600;
  color: #333;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;

  i {
    font-size: 32px;
    color: #66a6ff;
    margin-right: 8px;
  }

  span {
    color: #66a6ff;
    text-transform: uppercase;
    letter-spacing: 2px;
  }
}

.el-input {
  border-radius: 8px;
  background-color: #f3faff;
  border: 1px solid #e3e9f1;
  transition: all 0.3s;

  &:hover, &:focus {
    background-color: #ffffff;
    border: 1px solid #66a6ff;
    box-shadow: 0 0 8px rgba(102, 166, 255, 0.5);
  }
}

.el-button {
  border-radius: 8px;
  background-color: #66a6ff;
  color: #ffffff;
  box-shadow: 0 4px 10px rgba(102, 166, 255, 0.5);
  transition: all 0.3s;

  &:hover {
    background-color: #5a94e8;
    box-shadow: 0 6px 12px rgba(90, 148, 232, 0.5);
  }
}

.btns {
  display: flex;
  justify-content: space-between;
}

.login_form {
  margin-top: 20px; /* 减小 margin-top */
}
</style>

