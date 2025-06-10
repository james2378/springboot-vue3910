<template>
	<el-main class="bg">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
			<el-col v-if="user_group === '管理员' || $check_field('get','salesman_no') || $check_field('add','salesman_no') || $check_field('set','salesman_no')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="业务员编号" prop="salesman_no">
					<el-input id="salesman_no" v-model="form['salesman_no']" placeholder="请输入业务员编号"
							  v-if="user_group === '管理员' || (form['salesman_information_id'] && $check_field('set','salesman_no')) || (!form['salesman_information_id'] && $check_field('add','salesman_no'))" :disabled="disabledObj['salesman_no_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','salesman_no')">{{form['salesman_no']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','full_name') || $check_field('add','full_name') || $check_field('set','full_name')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="姓名" prop="full_name">
					<el-input id="full_name" v-model="form['full_name']" placeholder="请输入姓名"
							  v-if="user_group === '管理员' || (form['salesman_information_id'] && $check_field('set','full_name')) || (!form['salesman_information_id'] && $check_field('add','full_name'))" :disabled="disabledObj['full_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','full_name')">{{form['full_name']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','gender') || $check_field('add','gender') || $check_field('set','gender')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="性别" prop="gender">
					<el-select id="gender" v-model="form['gender']"
						v-if="user_group === '管理员' || (form['salesman_information_id'] && $check_field('set','gender')) || (!form['salesman_information_id'] && $check_field('add','gender'))">
						<el-option v-for="o in list_gender" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','gender')">{{form['gender']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','contact_information') || $check_field('add','contact_information') || $check_field('set','contact_information')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="联系方式" prop="contact_information">
					<el-input id="contact_information" v-model="form['contact_information']" placeholder="请输入联系方式"
							  v-if="user_group === '管理员' || (form['salesman_information_id'] && $check_field('set','contact_information')) || (!form['salesman_information_id'] && $check_field('add','contact_information'))" :disabled="disabledObj['contact_information_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','contact_information')">{{form['contact_information']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','department') || $check_field('add','department') || $check_field('set','department')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="部门" prop="department">
					<el-input id="department" v-model="form['department']" placeholder="请输入部门"
							  v-if="user_group === '管理员' || (form['salesman_information_id'] && $check_field('set','department')) || (!form['salesman_information_id'] && $check_field('add','department'))" :disabled="disabledObj['department_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','department')">{{form['department']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','post') || $check_field('add','post') || $check_field('set','post')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="职务" prop="post">
					<el-input id="post" v-model="form['post']" placeholder="请输入职务"
							  v-if="user_group === '管理员' || (form['salesman_information_id'] && $check_field('set','post')) || (!form['salesman_information_id'] && $check_field('add','post'))" :disabled="disabledObj['post_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','post')">{{form['post']}}</div>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8">
				<el-form-item>
					<el-button type="primary" @click="submit()">提交</el-button>
					<el-button @click="cancel()">取消</el-button>
				</el-form-item>
			</el-col>

		</el-form>
	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";

	export default {
		mixins: [mixin],
		data() {
			return {
				field: "salesman_information_id",
				url_add: "~/api/salesman_information/add?",
				url_set: "~/api/salesman_information/set?",
				url_get_obj: "~/api/salesman_information/get_obj?",
				url_upload: "~/api/salesman_information/upload?",

				query: {
					"salesman_information_id": 0,
				},

				form: {
					"salesman_no":'', // 业务员编号
					"full_name":'', // 姓名
					"gender":'', // 性别
					"contact_information":'', // 联系方式
					"department":'', // 部门
					"post":'', // 职务
					"salesman_information_id": 0, // ID

				},
				disabledObj:{
					"salesman_no_isDisabled": false,
					"full_name_isDisabled": false,
					"gender_isDisabled": false,
					"contact_information_isDisabled": false,
					"department_isDisabled": false,
					"post_isDisabled": false,
				},
				//性别选项列表
				list_gender: ['男','女'],

			}
		},
		methods: {

			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";
				if(this.form && form){
					Object.keys(this.form).forEach(key => {
						Object.keys(form).forEach(dbKey => {
							// if(dbKey === "charging_standard"){
							// 	this.form['charging_rules'] = form[dbKey];
							// 	this.disabledObj['charging_rules_isDisabled'] = true;
							// };
							if(key === dbKey){
								this.disabledObj[key+'_isDisabled'] = true;
							}
						})
					})
				}
				$.db.del("form");
				return param;
			},

			/**
			 * 获取对象之后
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_after(json, func){

			},

			is_view(){
				var bl = this.user_group == "管理员";

				if(!bl){
					bl = this.$check_action('/salesman_information/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/salesman_information/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/salesman_information/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/salesman_information/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/salesman_information/view','get');
					console.log(bl ? "你有视图查询权限视作有查询权限" : "你没有视图查询权限");
				}

				console.log(bl ? "具有当前页面的查看权，请注意这不代表你有字段的查看权" : "无权查看当前页，请注意即便有字段查询权限没有页面查询权限也不行");

				return bl;
			},
			/**
			 * 上传文件
			 * @param {Object} param
			 */
			uploadimg(param) {
				this.uploadFile(param.file, "avatar");
			},

		},
		created() {
		}
	}
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
</style>
