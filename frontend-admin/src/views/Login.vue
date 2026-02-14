<template>
  <div class="login-container">
    <div class="login-bg">
      <div class="bg-shape shape-1"></div>
      <div class="bg-shape shape-2"></div>
      <div class="bg-shape shape-3"></div>
    </div>
    <div class="login-content">
      <div class="login-left">
        <div class="brand-info">
          <div class="logo-icon">
            <el-icon :size="48"><HomeFilled /></el-icon>
          </div>
          <h1>智慧养老管理系统</h1>
          <p class="slogan">用心守护 · 智慧相伴</p>
          <div class="features">
            <div class="feature-item">
              <el-icon><UserFilled /></el-icon>
              <span>老人信息管理</span>
            </div>
            <div class="feature-item">
              <el-icon><Service /></el-icon>
              <span>护理服务预约</span>
            </div>
            <div class="feature-item">
              <el-icon><FirstAidKit /></el-icon>
              <span>健康数据监测</span>
            </div>
          </div>
        </div>
      </div>
      <div class="login-right">
        <div class="login-card">
          <div class="login-header">
            <h2>欢迎登录</h2>
            <p>请输入您的账号信息</p>
          </div>
          <el-form ref="formRef" :model="form" :rules="rules" @keyup.enter="handleLogin" class="login-form">
            <el-form-item prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名" size="large" clearable>
                <template #prefix>
                  <el-icon class="input-icon"><User /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="form.password" type="password" placeholder="请输入密码" size="large" show-password>
                <template #prefix>
                  <el-icon class="input-icon"><Lock /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="large" :loading="loading" @click="handleLogin" class="login-btn">
                <span v-if="!loading">登 录</span>
                <span v-else>登录中...</span>
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const form = reactive({ username: '', password: '' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    await userStore.login(form)
    ElMessage.success('登录成功')
    router.push('/')
  } finally {
    loading.value = false
  }
}
</script>


<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  position: relative;
  overflow: hidden;
  background: linear-gradient(135deg, #1a365d 0%, #2d5a87 50%, #3d7ea6 100%);
}

.login-bg {
  position: absolute;
  inset: 0;
  overflow: hidden;
  
  .bg-shape {
    position: absolute;
    border-radius: 50%;
    opacity: 0.1;
    background: linear-gradient(135deg, #fff 0%, transparent 100%);
  }
  
  .shape-1 {
    width: 600px;
    height: 600px;
    top: -200px;
    right: -100px;
    animation: float 20s ease-in-out infinite;
  }
  
  .shape-2 {
    width: 400px;
    height: 400px;
    bottom: -100px;
    left: -100px;
    animation: float 15s ease-in-out infinite reverse;
  }
  
  .shape-3 {
    width: 300px;
    height: 300px;
    top: 50%;
    left: 30%;
    animation: float 18s ease-in-out infinite;
  }
}

@keyframes float {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  50% { transform: translateY(-30px) rotate(5deg); }
}

.login-content {
  display: flex;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px;
  z-index: 1;
  align-items: center;
  justify-content: center;
  gap: 80px;
}

.login-left {
  flex: 1;
  color: #fff;
  
  .brand-info {
    .logo-icon {
      width: 80px;
      height: 80px;
      background: rgba(255, 255, 255, 0.15);
      border-radius: 20px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-bottom: 24px;
      backdrop-filter: blur(10px);
    }
    
    h1 {
      font-size: 36px;
      font-weight: 600;
      margin-bottom: 12px;
      letter-spacing: 2px;
    }
    
    .slogan {
      font-size: 18px;
      opacity: 0.8;
      margin-bottom: 48px;
    }
    
    .features {
      display: flex;
      flex-direction: column;
      gap: 20px;
      
      .feature-item {
        display: flex;
        align-items: center;
        gap: 16px;
        padding: 16px 24px;
        background: rgba(255, 255, 255, 0.1);
        border-radius: 12px;
        backdrop-filter: blur(10px);
        transition: all 0.3s ease;
        
        &:hover {
          background: rgba(255, 255, 255, 0.2);
          transform: translateX(10px);
        }
        
        .el-icon {
          font-size: 24px;
        }
        
        span {
          font-size: 16px;
        }
      }
    }
  }
}

.login-right {
  flex: 0 0 420px;
}

.login-card {
  background: #fff;
  border-radius: 24px;
  padding: 48px 40px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25);
}

.login-header {
  text-align: center;
  margin-bottom: 36px;
  
  h2 {
    font-size: 28px;
    color: #1a365d;
    margin-bottom: 8px;
    font-weight: 600;
  }
  
  p {
    color: #64748b;
    font-size: 14px;
  }
}

.login-form {
  .el-form-item {
    margin-bottom: 24px;
  }
  
  :deep(.el-input__wrapper) {
    padding: 4px 16px;
    border-radius: 12px;
    box-shadow: 0 0 0 1px #e2e8f0;
    transition: all 0.3s ease;
    
    &:hover, &.is-focus {
      box-shadow: 0 0 0 2px #3d7ea6;
    }
  }
  
  :deep(.el-input__inner) {
    height: 44px;
  }
  
  .input-icon {
    font-size: 18px;
    color: #94a3b8;
  }
}

.login-btn {
  width: 100%;
  height: 48px;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 500;
  background: linear-gradient(135deg, #2d5a87 0%, #3d7ea6 100%);
  border: none;
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 10px 20px -10px rgba(45, 90, 135, 0.5);
  }
  
  &:active {
    transform: translateY(0);
  }
}

.login-footer {
  text-align: center;
  margin-top: 24px;
  padding-top: 24px;
  border-top: 1px solid #e2e8f0;
  
  p {
    color: #94a3b8;
    font-size: 13px;
  }
}

@media (max-width: 900px) {
  .login-content {
    flex-direction: column;
    padding: 20px;
    gap: 40px;
  }
  
  .login-left {
    text-align: center;
    
    .brand-info {
      .logo-icon {
        margin: 0 auto 24px;
      }
      
      h1 {
        font-size: 28px;
      }
      
      .features {
        display: none;
      }
    }
  }
  
  .login-right {
    flex: none;
    width: 100%;
    max-width: 400px;
  }
}
</style>
