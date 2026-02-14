<template>
  <div class="page-container">
    <div class="card">
      <div class="card-header">
        <span class="card-title">操作日志</span>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="username" label="操作人" width="120" />
        <el-table-column prop="operation" label="操作" width="120" />
        <el-table-column prop="method" label="方法" min-width="250" show-overflow-tooltip />
        <el-table-column prop="ip" label="IP地址" width="140" />
        <el-table-column prop="createTime" label="操作时间" width="180" />
      </el-table>
      <el-pagination v-model:current-page="page.current" v-model:page-size="page.size" :total="page.total"
        layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 16px; justify-content: flex-end;" />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getLogPage } from '../api'

const loading = ref(false)
const tableData = ref([])
const page = reactive({ current: 1, size: 10, total: 0 })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getLogPage({ current: page.current, size: page.size })
    tableData.value = res.data.records
    page.total = res.data.total
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>
