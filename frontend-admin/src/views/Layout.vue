<template>
  <el-container class="layout-container">
    <el-aside width="220px" class="aside">
      <div class="logo">
        <el-icon size="24"><House /></el-icon>
        <span>智慧养老</span>
      </div>
      <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
        <el-menu-item index="/dashboard"><el-icon><DataAnalysis /></el-icon><span>首页概览</span></el-menu-item>
        <el-menu-item index="/elder"><el-icon><User /></el-icon><span>老人管理</span></el-menu-item>
        <el-menu-item index="/worker"><el-icon><Avatar /></el-icon><span>护工管理</span></el-menu-item>
        <el-menu-item index="/service"><el-icon><Service /></el-icon><span>服务管理</span></el-menu-item>
        <el-menu-item index="/order"><el-icon><List /></el-icon><span>订单管理</span></el-menu-item>
        <el-menu-item index="/health"><el-icon><FirstAidKit /></el-icon><span>健康管理</span></el-menu-item>
        <el-menu-item index="/log"><el-icon><Document /></el-icon><span>操作日志</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <span class="page-title">{{ route.meta.title }}</span>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32" icon="User" />
              <span>{{ userStore.userInfo?.realName || 'Admin' }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { useUserStore } from '../store/user'
import { onMounted } from 'vue'

const route = useRoute()
const userStore = useUserStore()

onMounted(() => {
  userStore.fetchUserInfo()
})

const handleCommand = (cmd) => {
  if (cmd === 'logout') {
    userStore.logout()
  }
}
</script>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;
}

.aside {
  background: #304156;
  
  .logo {
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    color: #fff;
    font-size: 18px;
    font-weight: 600;
    border-bottom: 1px solid rgba(255,255,255,0.1);
  }
  
  .el-menu {
    border-right: none;
  }
}

.header {
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 1px 4px rgba(0,0,0,0.1);
  
  .page-title {
    font-size: 16px;
    font-weight: 600;
    color: #303133;
  }
  
  .user-info {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    color: #606266;
  }
}

.main {
  background: #f5f7fa;
  padding: 0;
}
</style>
