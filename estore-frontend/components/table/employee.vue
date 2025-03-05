<script setup lang="ts">

import NewEntryDialogue from "~/components/table/NewEntryDialogue.vue";
import { DialogTrigger } from "~/components/ui/dialog";
import { Button } from "~/components/ui/button";
import { Icon } from "@iconify/vue";

interface EmployeeState {
  employees: any[];
  page: number;
  pageSize: number;
}

function createUpdateDto(employee: any) {
  return {
    firstName: employee.firstName,
    lastName: employee.lastName,
    patronymic: employee.patronymic,
    birthDate: new Date(employee.birthDate).toLocaleDateString("ru-RU"),
    gender: employee.gender ? 1 : 0,
    shopId: employee.shop.id,
    positionId: employee.positionType.id
  }
}

const employeeState = reactive<EmployeeState>({
  employees: [],
  page: 0,
  pageSize: 10,
})

async function fetchEmployees(page: number)  {
  employeeState.employees = await $fetch("http://localhost:8081/estore/api/employee/?page=" + employeeState.page + "&pageSize=" + employeeState.pageSize) as any;
}

watch(() => employeeState.page,
    (page) => {
      fetchEmployees(page);
    }
)

onMounted(async () => {
  await fetchEmployees(0);
})

const keyFilterSet = {
  'firstName': {
    regex: new RegExp("[а-яА-ЯA-Za-z]{1,150}"),
    format: "Строка до 150",
    name: 'Имя'
  },
  'lastName': {
    regex: new RegExp("[а-яА-ЯA-Za-z]{1,150}"),
    format: "Строка до 150",
    name: 'Фамилия'
  },
  'patronymic': {
    regex: new RegExp("[а-яА-ЯA-Za-z]{1,150}"),
    format: "Строка до 150",
    name: 'Отчество'
  },
  'birthDate': {
    regex: new RegExp("(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[0-2]).(19|20)\\d{2}"),
    format: "dd-MM-yyyy",
    name: 'День рождения'
  },
  'positionId': {
    regex: new RegExp("[0-9]+"),
    format: "Число",
    name: 'Идентификатор должности'
  },
  'shopId': {
    regex: new RegExp("[0-9]+"),
    format: "Число",
    name: 'Идентификатор магазина'
  },
  'gender': {
    regex: new RegExp("[0-1]"),
    format: "1 - Мужской, 2 - Женский",
    name: 'Пол'
  }

}

</script>

<template>
  <Dialog>
    <DialogTrigger as-child>
      <Button variant="outline">
        Создать сотрудника
      </Button>
    </DialogTrigger>
    <NewEntryDialogue @newEntry="fetchEmployees(employeeState.page)" :keySet="keyFilterSet" name="employee" endpoint="/estore/api/employee" />
  </Dialog>

  <div class="flex items-center">
    <Button @click="employeeState.page--" variant="outline" :disabled="employeeState.page == 0">
      <Icon icon="radix-icons:double-arrow-left" />
    </Button>
    <p class="mx-2">{{ employeeState.page + 1 }}</p>
    <Button @click="employeeState.page++" variant="outline" :disabled="employeeState.employees.length !== employeeState.pageSize">
      <Icon icon="radix-icons:double-arrow-right" />
    </Button>
  </div>

  <Table>
    <TableHeader>
      <TableRow>
        <TableHead class="w-[100px]">
          id
        </TableHead>
        <TableHead>firstname</TableHead>
        <TableHead>lastname</TableHead>
        <TableHead class="text-right">
          patronymic
        </TableHead>
        <TableHead class="text-center">birthDay</TableHead>
        <TableHead class="text-center">position</TableHead>
        <TableHead class="text-center">shop</TableHead>
        <TableHead class="text-center">gender</TableHead>
        <TableHead class="text-center">electroType</TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <TableRow v-for="employee in employeeState.employees" :key="employee.id">
        <template v-if="employee">  <TableCell class="font-medium">
          {{ employee.id }}
        </TableCell>
          <TableCell>{{ employee.firstName }}</TableCell>
          <TableCell>{{ employee.lastName }}</TableCell>
          <TableCell class="text-right">
            {{ employee.patronymic }}
          </TableCell>
          <TableCell>
            <p class="max-w-[200px] truncate">
              {{ new Date(employee.birthDate).toLocaleDateString("ru-RU") }}</p>
          </TableCell>
          <TableCell>
            {{ employee.positionType.name }}
          </TableCell>
          <TableCell>
            {{ employee.shop.name }}
          </TableCell>
          <TableCell>
            {{ employee.gender }}
          </TableCell>
          <TableCell>
            <p v-for="type in employee.electroTypes">
              {{ type.name }}</p>
          </TableCell>
        </template>
          <TableCell>
            <Dialog>
              <DialogTrigger>
                <Button variant="outline">
                  <Icon icon="radix-icons:pencil-1" />
                </Button>
              </DialogTrigger>
              <NewEntryDialogue :itemToEdit="createUpdateDto(employee)"
                                :editing="true"
                                @newEntry="fetchEmployees(employeeState.page)"
                                :keySet="keyFilterSet"
                                name="purchase" :endpoint="'/estore/api/employee/' + employee.id" />
            </Dialog>
          </TableCell>
      </TableRow>
    </TableBody>
  </Table>
</template>

<style scoped>

</style>