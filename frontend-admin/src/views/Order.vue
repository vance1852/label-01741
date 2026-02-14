<template>
  <div class="page-container">
    <div class="card">
      <div class="card-header">
        <span class="card-title">订单管理</span>
        <el-button type="primary" @click="openDialog()">创建订单</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="orderNo" label="订单编号" width="200" />
        <el-table-column prop="elderName" label="老人姓名" width="100" />
        <el-table-column prop="serviceName" label="服务项目" width="120" />
        <el-table-column prop="workerName" label="护工" width="100" />
        <el-table-column prop="appointmentTime" label="预约时间" width="180" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusText[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="150" show-overflow-tooltip />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status === 0" link type="primary" @click="updateStatus(row.id, 1)">开始服务</el-button>
            <el-button v-if="row.status === 1" link type="success" @click="updateStatus(row.id, 2)">完成</el-button>
            <el-button v-if="row.status === 0" link type="danger" @click="updateStatus(row.id, 3)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="page.current" v-model:page-size="page.size" :total="page.total"
        layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 16px; justify-content: flex-end;" />
    </div>

    <el-dialog v-model="dialogVisible" title="创建订单" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="选择老人" prop="elderId">
          <el-select v-model="form.elderId" placeholder="请选择老人" style="width: 100%">
            <el-option v-for="item in elderList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="服务项目" prop="serviceId">
          <el-select v-model="form.serviceId" placeholder="请选择服务" style="width: 100%">
            <el-option v-for="item in serviceList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="指派护工">
          <el-select v-model="form.workerId" placeholder="请选择护工" clearable style="width: 100%">
            <el-option v-for="item in workerList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="预约时间" prop="appointmentTime">
          <el-date-picker v-model="form.appointmentTime" type="datetime" 
            value-format="YYYY-MM-DD HH:mm:ss" 
            :disabled-date="(date) => date < new Date(new Date().setHours(0,0,0,0))"
            placeholder="请选择预约时间"
            style="width: 100%" />
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
import { getOrderPage, saveOrder, updateOrderStatus, getElderList, getServiceList, getWorkerList } from '../api'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const submitLoading = ref(false)
const dialogVisible = ref(false)
const tableData = ref([])
const elderList = ref([])
const serviceList = ref([])
const workerList = ref([])
const page = reactive({ current: 1, size: 10, total: 0 })
const formRef = ref()
const form = ref({})

// 预约时间验证
const validateAppointmentTime = (rule, value, callback) => {
  if (value) {
    const appointmentDate = new Date(value)
    const now = new Date()
    if (appointmentDate < now) {
      callback(new Error('预约时间不能是过去的时间'))
    } else {
      callback()
    }
  } else {
    callback()
  }
}

const rules = {
  elderId: [{ required: true, message: '请选择老人', trigger: 'change' }],
  serviceId: [{ required: true, message: '请选择服务', trigger: 'change' }],
  appointmentTime: [{ validator: validateAppointmentTime, trigger: 'change' }],
  remark: [{ max: 500, message: '备注长度不能超过500个字符', trigger: 'blur' }]
}
const statusText = ['待处理', '进行中', '已完成', '已取消']
const statusType = ['warning', 'primary', 'success', 'info']

const loadData = async () => {
  loading.value = true
  try {
    const res = await getOrderPage({ current: page.current, size: page.size })
    tableData.value = res.data.records
    page.total = res.data.total
  } finally {
    loading.value = false
  }
}

const loadOptions = async () => {
  const [e, s, w] = await Promise.all([getElderList(), getServiceList(), getWorkerList()])
  elderList.value = e.data
  serviceList.value = s.data
  workerList.value = w.data
}

const openDialog = () => {
  form.value = {}
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  submitLoading.value = true
  try {
    await saveOrder(form.value)
    ElMessage.success('创建成功')
    dialogVisible.value = false
    loadData()
  } finally {
    submitLoading.value = false
  }
}

const updateStatus = async (id, status) => {
  await updateOrderStatus({ id, status })
  ElMessage.success('操作成功')
  loadData()
}

onMounted(() => {
  loadData()
  loadOptions()
})
</script>
