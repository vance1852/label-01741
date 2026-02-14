<template>
  <div class="page-container">
    <div class="card">
      <div class="card-header">
        <span class="card-title">健康管理</span>
        <el-button type="primary" @click="openDialog()">新增记录</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="elderName" label="老人姓名" width="100" />
        <el-table-column prop="bloodPressureHigh" label="收缩压" width="90">
          <template #default="{ row }">{{ row.bloodPressureHigh || '-' }}</template>
        </el-table-column>
        <el-table-column prop="bloodPressureLow" label="舒张压" width="90">
          <template #default="{ row }">{{ row.bloodPressureLow || '-' }}</template>
        </el-table-column>
        <el-table-column prop="heartRate" label="心率" width="80">
          <template #default="{ row }">{{ row.heartRate || '-' }}</template>
        </el-table-column>
        <el-table-column prop="bloodSugar" label="血糖" width="80">
          <template #default="{ row }">{{ row.bloodSugar || '-' }}</template>
        </el-table-column>
        <el-table-column prop="temperature" label="体温" width="80">
          <template #default="{ row }">{{ row.temperature || '-' }}</template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="150" show-overflow-tooltip />
        <el-table-column prop="recordTime" label="记录时间" width="180" />
      </el-table>
      <el-pagination v-model:current-page="page.current" v-model:page-size="page.size" :total="page.total"
        layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 16px; justify-content: flex-end;" />
    </div>

    <el-dialog v-model="dialogVisible" title="新增健康记录" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="选择老人" prop="elderId">
          <el-select v-model="form.elderId" placeholder="请选择老人" style="width: 100%">
            <el-option v-for="item in elderList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="收缩压" prop="bloodPressureHigh">
              <el-input-number v-model="form.bloodPressureHigh" :min="60" :max="250" style="width: 100%" placeholder="60-250" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="舒张压" prop="bloodPressureLow">
              <el-input-number v-model="form.bloodPressureLow" :min="40" :max="150" style="width: 100%" placeholder="40-150" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="心率">
              <el-input-number v-model="form.heartRate" :min="30" :max="200" style="width: 100%" placeholder="30-200" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="血糖">
              <el-input-number v-model="form.bloodSugar" :min="1" :max="30" :precision="1" style="width: 100%" placeholder="1-30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="体温">
          <el-input-number v-model="form.temperature" :min="34" :max="42" :precision="1" style="width: 100%" placeholder="34-42" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="2" maxlength="500" show-word-limit />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getHealthPage, saveHealth, getElderList } from '../api'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const submitLoading = ref(false)
const dialogVisible = ref(false)
const tableData = ref([])
const elderList = ref([])
const page = reactive({ current: 1, size: 10, total: 0 })
const formRef = ref()
const form = ref({})

// 血压验证
const validateBloodPressure = (rule, value, callback) => {
  if (form.value.bloodPressureHigh && form.value.bloodPressureLow) {
    if (form.value.bloodPressureHigh <= form.value.bloodPressureLow) {
      callback(new Error('收缩压应大于舒张压'))
    } else {
      callback()
    }
  } else {
    callback()
  }
}

const rules = {
  elderId: [{ required: true, message: '请选择老人', trigger: 'change' }],
  bloodPressureHigh: [{ validator: validateBloodPressure, trigger: 'blur' }],
  bloodPressureLow: [{ validator: validateBloodPressure, trigger: 'blur' }],
  remark: [{ max: 500, message: '备注长度不能超过500个字符', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getHealthPage({ current: page.current, size: page.size })
    tableData.value = res.data.records
    page.total = res.data.total
  } finally {
    loading.value = false
  }
}

const loadElders = async () => {
  const res = await getElderList()
  elderList.value = res.data
}

const openDialog = () => {
  form.value = { temperature: 36.5 }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  submitLoading.value = true
  try {
    await saveHealth(form.value)
    ElMessage.success('添加成功')
    dialogVisible.value = false
    loadData()
  } finally {
    submitLoading.value = false
  }
}

onMounted(() => {
  loadData()
  loadElders()
})
</script>
