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
          <div class="stat-value">{{ warnings.length }}</div>
          <div class="stat-label">健康预警</div>
        </div>
      </el-col>
    </el-row>
    
    <div class="card" style="margin-top: 24px;" v-if="warnings.length > 0">
      <div class="card-header">
        <span class="card-title">健康预警</span>
      </div>
      <div class="warning-list">
        <div v-for="warning in warnings" :key="warning.id" class="warning-card" :class="'level-' + warning.warningLevel">
          <div class="warning-header">
            <span class="warning-type">{{ getWarningTypeText(warning.warningType) }}</span>
            <span class="warning-level-tag">{{ getLevelText(warning.warningLevel) }}</span>
          </div>
          <div class="warning-content">
            <div class="warning-item">
              <span class="warning-label">老人姓名：</span>
              <span class="warning-value">{{ warning.elderName }}</span>
            </div>
            <div class="warning-item">
              <span class="warning-label">异常指标：</span>
              <span class="warning-value">{{ warning.abnormalIndicators }}</span>
            </div>
            <div class="warning-item">
              <span class="warning-label">记录时间：</span>
              <span class="warning-value">{{ warning.createTime }}</span>
            </div>
          </div>
          <div class="warning-actions">
            <el-button type="primary" size="small" @click="handleWarningClick(warning.id)">标记为已处理</el-button>
          </div>
        </div>
      </div>
    </div>
    
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
import { getDashboardStats, getDashboardWarnings, handleWarning } from '../api'
import { ElMessage } from 'element-plus'

const stats = ref({})
const warnings = ref([])

const getWarningTypeText = (type) => {
  const typeMap = {
    HIGH_BLOOD_PRESSURE: '高血压预警',
    HIGH_BLOOD_SUGAR: '高血糖预警',
    HIGH_TEMPERATURE: '发热预警',
    HEART_RATE: '心率异常预警'
  }
  return typeMap[type] || type
}

const getLevelText = (level) => {
  const levelMap = {
    1: '一般',
    2: '重要',
    3: '紧急'
  }
  return levelMap[level] || level
}

const loadWarnings = async () => {
  const res = await getDashboardWarnings()
  warnings.value = res.data
}

const handleWarningClick = async (id) => {
  await handleWarning(id)
  ElMessage.success('已标记为已处理')
  loadWarnings()
}

onMounted(async () => {
  const res = await getDashboardStats()
  stats.value = res.data
  loadWarnings()
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

.warning-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 16px;
}

.warning-card {
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  padding: 16px;
  background: #fff;
  
  &.level-1 {
    border-left: 4px solid #67c23a;
  }
  
  &.level-2 {
    border-left: 4px solid #e6a23c;
  }
  
  &.level-3 {
    border-left: 4px solid #f56c6c;
  }
}

.warning-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f0f0f0;
}

.warning-type {
  font-size: 16px;
  font-weight: bold;
  color: #303133;
}

.warning-level-tag {
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
  background: #f0f0f0;
  color: #606266;
}

.warning-content {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 12px;
}

.warning-item {
  display: flex;
  font-size: 14px;
}

.warning-label {
  color: #909399;
  min-width: 80px;
}

.warning-value {
  color: #303133;
  flex: 1;
}

.warning-actions {
  display: flex;
  justify-content: flex-end;
}
</style>
