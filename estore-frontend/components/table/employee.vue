<script setup lang="ts">

import NewEntryDialogue from "~/components/table/NewEntryDialogue.vue";
import { DialogTrigger } from "~/components/ui/dialog";
import { Button } from "~/components/ui/button";

const employees = ref([])

async function fetchEmployee()  {
  employees.value = await $fetch("http://localhost:8081/estore/api/employee") as any;

}

onMounted(async () => {
  fetchEmployee();
})

const keyFilterSet = {
  'firstName': {
    regex: new RegExp("[а-яА-ЯA-Za-z]{1,150}"),
    format: "Строка до 150"
  },
  'lastName': {
    regex: new RegExp("[а-яА-ЯA-Za-z]{1,150}"),
    format: "Строка до 150"
  },
  'patronymic': {
    regex: new RegExp("[а-яА-ЯA-Za-z]{1,150}"),
    format: "Строка до 150"
  },
  'birthDate': {
    regex: new RegExp("(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[0-2]).(19|20)\\d{2}"),
    format: "dd-MM-yyyy"
  },
  'positionId': {
    regex: new RegExp("[0-9]+"),
    format: "Число"
  },
  'shopId': {
    regex: new RegExp("[0-9]+"),
    format: "Число"
  },
  'gender': {
    regex: new RegExp("[0-1]"),
    format: "1 - Да, 0 - нет"
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
    <NewEntryDialogue @newEntry="fetchEmployee()" :keySet="keyFilterSet" name="employee" endpoint="/estore/api/employee" />
  </Dialog>
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
      <TableRow v-for="employee in employees" :key="employee.id">
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
              {{ employee.birthDate }}</p>
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
            {{ employee.electroTypes }}
          </TableCell></template>

      </TableRow>
    </TableBody>
  </Table>
</template>

<style scoped>

</style>