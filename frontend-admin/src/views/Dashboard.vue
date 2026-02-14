<template>
  <div class="page-container">
    <el-row :gutter="16">
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-value">{{ stats.elderCount || 0 }}</div>
          <div class="stat-label">在院老人</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card success">
          <div class="stat-value">{{ stats.workerCount || 0 }}</div>
          <div class="stat-label">在职护工</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card warning">
          <div class="stat-value">{{ stats.pendingOrders || 0 }}</div>
          <div class="stat-label">待处理订单</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card danger">
          <div class="stat-value">{{ stats.completedOrders || 0 }}</div>
          <div class="stat-label">已完成订单</div>
        </div>
      </el-col>
    </el-row>
    
    <div class="card" style="margin-top: 24px;">
      <div class="card-header">
        <span class="card-title">系统简介</span>
      </div>
      <div class="intro-content">
        <p>智慧养老管理系统是一套面向养老机构的综合管理平台，提供老人信息管理、护工管理、服务预约、健康监测等核心功能。</p>
        <el-row :gutter="24" style="margin-top: 24px;">
          <el-col :span="8">
            <div class="feature-item">
              <el-icon size="32" color="#409EFF"><User /></el-icon>
              <h4>老人管理</h4>
              <p>完整的老人档案管理，包含基本信息、健康状态、紧急联系人等</p>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="feature-item">
              <el-icon size="32" color="#67C23A"><FirstAidKit /></el-icon>
              <h4>健康监测</h4>
              <p>记录血压、心率、血糖等健康指标，实时掌握老人健康状况</p>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="feature-item">
              <el-icon size="32" color="#E6A23C"><Service /></el-icon>
              <h4>服务预约</h4>
              <p>在线预约护理服务，智能分配护工，提升服务效率</p>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getDashboardStats } from '../api'

const stats = ref({})

onMounted(async () => {
  const res = await getDashboardStats()
  stats.value = res.data
})
</script>

<style lang="scss" scoped>
.intro-content {
  color: #606266;
  line-height: 1.8;
}

.feature-item {
  text-align: center;
  padding: 24px;
  background: #f5f7fa;
  border-radius: 8px;
  
  h4 {
    margin: 16px 0 8px;
    color: #303133;
  }
  
  p {
    font-size: 13px;
    color: #909399;
  }
}
</style>
