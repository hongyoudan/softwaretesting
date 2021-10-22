<template>
  <el-container>
    <el-header>《软件测试》实验程序</el-header>
    <el-main>
      <el-row :gutter="20">

        <!-- 三角形问题 -->
        <el-col :span="8">
          <el-card>
            <h3>三角形问题</h3>
            <el-divider content-position="center">说明</el-divider>
            <p>
              规定输入三个整数a、b、c分别作为三边的边长构成三角形。<br>
              通过程序判定所构成的三角形的类型（等边三角形、等腰三角形、一般三角形、构不成三角形），并在屏幕上输出。
            </p>
            <el-form :model="triangleForm" :rules="rules" ref="triangleForm">
              <el-form-item label="三角形的边a" prop="a" label-width="110px">
                <el-input v-model.number="triangleForm.a" placeholder="请输入三角形的边a"/>
              </el-form-item>
              <el-form-item label="三角形的边b" prop="b" label-width="110px">
                <el-input v-model.number="triangleForm.b" placeholder="请输入三角形的边b"/>
              </el-form-item>
              <el-form-item label="三角形的边c" prop="c" label-width="110px">
                <el-input v-model.number="triangleForm.c" placeholder="请输入三角形的边c"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="handleTriangleSubmit">提 交</el-button>
                <el-button @click="resetForm('triangleForm')">重 置</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>

        <!-- 电话号码有效性检查功能 -->
        <el-col :span="8">
          <el-card>
            <h3>电话号码有效性检查功能</h3>
            <el-divider content-position="center">说明</el-divider>
            <p>
              执行电话号码有效性检查功能。 <br>
              中国的固定电话号码由两部分组成。这两部分的名称和内容分别是：<br>
              地区码：以0开头的三位或者四位数字（包括0）。<br>
              电话号码：以非0、非1开头的七位或者八位数字。
            </p>
            <el-form :model="checkTelForm" :rules="rules" ref="checkTelForm">
              <el-form-item>
                <el-col :span="12">
                  <el-form-item label="地区码" prop="rc">
                    <el-input placeholder="请输入地区码" v-model="checkTelForm.rc">
                      <template slot="append">一</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="电话号码" prop="n">
                    <el-input placeholder="请输入电话号码" v-model="checkTelForm.n"/>
                  </el-form-item>
                </el-col>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="handleCheckTelForm">提 交</el-button>
                <el-button @click="resetForm('checkTelForm')">重 置</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>

        <!-- 日期下一天 -->
        <el-col :span="8">
          <el-card>
            <h3>日期下一天功能</h3>
            <el-divider content-position="center">说明</el-divider>
            <p>
              输入三个整数：年、月和日，函数的输出为输入日期后一天的日期。<br>
              例如，输入为2021年10月22日，则函数的输出为2021年10月23日，<br>
              年满足[1920,2050]。
            </p>
            <el-form :model="nextDateForm" :rules="rules" ref="nextDateForm">
              <el-form-item>
                <el-col :span="8">
                  <el-form-item prop="year" label="年">
                    <el-input placeholder="请输入年" v-model.number="nextDateForm.year">
                      <template slot="append">年</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item prop="month" label="月">
                    <el-input placeholder="请输入月" v-model.number="nextDateForm.month">
                      <template slot="append">月</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item prop="day" label="日">
                    <el-input placeholder="请输入日" v-model.number="nextDateForm.day">
                      <template slot="append">日</template>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-form-item>
              <el-form-item>
                <el-col>
                  <el-form-item label="下一天" label-width="100px">
                    <el-input placeholder="点击提交，自动计算出下一天" v-model="nextDateForm.nextDate" :disabled="true"/>
                  </el-form-item>
                </el-col>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="handleNextDateForm">提 交</el-button>
                <el-button @click="resetForm('nextDateForm')">重 置</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
    <el-footer th:class="el-footer">©2021 Hayden. All rights reserved.</el-footer>
  </el-container>
</template>

<script>
export default {
  data: function () {

    // 定义三角形边长校验规则
    var validateSide = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('请输入三角形的边长！'));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error('请输入数字值！'));
        } else {
          if (value < 1 || value > 200 || value === 0) {
            callback(new Error('边长必须在[1,200]之间！'));
          } else {
            callback();
          }
        }
      }, 1000);
    };

    // 定义地区码校验规则
    var validateRC = (rule, value, callback) => {
      var pattRC = new RegExp("^([0][0-9]{2,3})$");
      if (value === '') {
        return callback(new Error('请输入地区码！'));
      }
      setTimeout(() => {
        if (!pattRC.test(value)) {
          callback(new Error('必须以0开头的三位或者四位数字（包括0）！'));
        } else {
          callback();
        }
      }, 1000);
    };
    // 定义电话号码校验规则
    var validateN = (rule, value, callback) => {
      var pattN = new RegExp("^([2-9][0-9]{6,7})$");
      if (value === '') {
        return callback(new Error('请输入电话号码！'));
      }
      setTimeout(() => {
        if (!pattN.test(value)) {
          callback(new Error('必须以非0、非1开头的七位或者八位数字！'));
        } else {
          callback();
        }
      }, 1000)
    };

    // 定义日期下一天验证规则
    var validateYear = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('请输入年！'));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error('请输入数字值！'));
        } else {
          if (value < 1920 || value > 2050) {
            callback(new Error('年份必须在1920至2050之间！'));
          } else {
            callback();
          }
        }
      }, 1000);
    }
    var validateMonth = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('请输入月！'));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error('请输入数字值！'));
        } else {
          if (value < 1 || value > 12) {
            callback(new Error('月份必须在1至12之间！'));
          } else {
            callback();
          }
        }
      }, 1000);
    }
    var validateDay = (rule, value, callback) => {
      var year = this.nextDateForm.year;
      var month = this.nextDateForm.month;
      if (value === '') {
        return callback(new Error('请输入日！'));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error('请输入数字值！'));
        } else if ((month === 1 && value > 31) || (month === 3 && value > 31) || (month === 4 && value > 30)
          || (month === 5 && value > 31) || (month === 6 && value > 30) || (month === 7 && value > 31)
          || (month === 8 && value > 31) || (month === 9 && value > 30) || (month === 10 && value > 31)
          || (month === 11 && value > 30) || (month === 12 && value > 31)) {
          callback(new Error('请输入正确的天数！'));
        } else if (month === 2) {
          if (year % 4 === 0 && year % 100 !== 0 || year % 400 === 0) {
            if (value < 1 || value > 29) {
              callback(new Error('请输入正确的天数！'));
            } else {
              callback()
            }
          } else {
            if (value < 1 || value > 28) {
              callback(new Error('请输入正确的天数！'));
            } else {
              callback()
            }

          }
        } else {
          callback()
        }
      }, 1000);
    }

    return {
      triangleForm: {
        a: '',
        b: '',
        c: ''
      },
      checkTelForm: {
        rc: '',
        n: ''
      },
      nextDateForm: {
        year: '',
        month: '',
        day: '',
        nextDate: ''
      },

      rules: {
        a: [
          {required: true, validator: validateSide, trigger: 'blur'}
        ],
        b: [
          {required: true, validator: validateSide, trigger: 'blur'}
        ],
        c: [
          {required: true, validator: validateSide, trigger: 'blur'}
        ],
        rc: [
          {required: true, validator: validateRC, trigger: 'blur'}
        ],
        n: [
          {required: true, validator: validateN, trigger: 'blur'}
        ],
        year: [
          {required: true, validator: validateYear, trigger: 'blur'}
        ],
        month: [
          {required: true, validator: validateMonth, trigger: 'blur'}
        ],
        day: [
          {required: true, validator: validateDay, trigger: 'blur'}
        ],
      }
    };
  },
  methods: {
    handleTriangleSubmit() {
      this.$refs.triangleForm.validate(valid => {
        if (valid) {
          this.$axios({
            method: "post",
            url: "http://localhost:8081/triangle",
            data: this.triangleForm
          }).then((response) => {          //这里使用了ES6的语法
            if (response.data.errno === 0) {
              this.$message({
                message: response.data.data,
                type: "success"
              });
            } else {
              this.$message.error(response.data.errmsg);
            }

          })

        } else {
          return false
        }
      })
    },

    handleCheckTelForm() {
      this.$refs.checkTelForm.validate(valid => {
        if (valid) {
          this.$axios({
            method: "post",
            url: "http://localhost:8081/checktel",
            data: this.checkTelForm
          }).then((response) => {
            if (response.data.errno === 0) {
              this.$message({
                message: response.data.data,
                type: "success"
              });
            } else {
              this.$message.error(response.data.errmsg);
            }
          })
        } else {
          return false
        }

      })
    },

    handleNextDateForm() {
      this.$refs.nextDateForm.validate(valid => {
        if (valid) {
          this.$axios({
            method: "post",
            url: "http://localhost:8081/nextdate",
            data: this.nextDateForm
          }).then((response) => {
            if (response.data.errno === 0) {
              this.$message({
                message: "成功！",
                type: "success"
              });
              this.nextDateForm.nextDate = response.data.data;
            } else {
              this.$message.error(response.data.errmsg);
            }
          })
        } else {
          return false
        }

      })
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}

</script>

<style>
.el-header {
  background-color: #409EFF;
  color: #fff;
  text-align: center;
  line-height: 60px;
}

.el-footer {
  background-color: #DCDFE6;
  color: #909399;
  text-align: center;
  line-height: 60px;
}

.el-main {
  min-height: calc(100vh - 140px);
  background-color: #F5F7FA;
  color: #333;
  text-align: center;
}

p {
  color: #909399;
  font-size: 14px;
  margin-bottom: 30px;
  text-align: center;
}

.el-row {
  margin-bottom: 20px;
}

.el-form-item .el-button {
  margin-top: 10px;
}

</style>
