import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class RecipeNode {
    String text;
    RecipeNode[] choices;
    String finalDish;

    public RecipeNode(String text, RecipeNode[] choices) {
        this.text = text;
        this.choices = choices;
        this.finalDish = null;
    }

    public RecipeNode(String finalDish) {
        this.text = "ผลลัพธ์ที่ได้คือ: " + finalDish;
        this.choices = null;
        this.finalDish = finalDish;
    }

    public boolean isLeaf() {
        return choices == null;
    }
}

public class LittleChefGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        // --- 1. สร้างกิ่งก้านสาขาของการทำอาหาร ---

        RecipeNode tomYum = new RecipeNode("ต้มยำกุ้ง");
        RecipeNode badShrimp = new RecipeNode("กุ้งทอดไหม้ๆ (กินไม่ได้)");
        RecipeNode shrimpCookMethod = new RecipeNode("เลือกวิธีทำกุ้ง:", new RecipeNode[]{tomYum, badShrimp});
        shrimpCookMethod.choices[0].text = "ต้มใส่เครื่องสมุนไพร";
        shrimpCookMethod.choices[1].text = "โยนลงกระทะทอดไฟแรง";

        RecipeNode friedChicken = new RecipeNode("ไก่ทอดกรอบ");
        RecipeNode rawChicken = new RecipeNode("ไก่ดิบ (ลูกค้าท้องเสีย)");
        RecipeNode chickenPrep = new RecipeNode("เตรียมไก่อย่างไรดี:", new RecipeNode[]{friedChicken, rawChicken});
        chickenPrep.choices[0].text = "ชุบแป้งแล้วนำไปทอด";
        chickenPrep.choices[1].text = "เสิร์ฟสดๆ เลย";

        RecipeNode spaghetti = new RecipeNode("สปาเกตตีซอสมะเขือเทศ");
        RecipeNode pastaPrep = new RecipeNode("เส้นพร้อมแล้ว ทำไงต่อ:", new RecipeNode[]{spaghetti});
        pastaPrep.choices[0].text = "ผัดกับซอสมะเขือเทศ";

        RecipeNode cleanFood = new RecipeNode("สลัดผักอาหารคลีน");

        RecipeNode root = new RecipeNode("เลือกวัตถุดิบหลัก:", new RecipeNode[]{
                shrimpCookMethod, chickenPrep, pastaPrep, cleanFood
        });
        root.choices[0].text = "กุ้งสด";
        root.choices[1].text = "เนื้อไก่";
        root.choices[2].text = "เส้นพาสต้า";
        root.choices[3].text = "ผักรวมออร์แกนิก";

        // --- 2. ระบบจัดการออร์เดอร์แบบไม่ซ้ำ ---
        // ใช้ List แทน Array เพื่อใช้คำสั่งสลับลำดับ (Shuffle) ได้
        List<String> orderList = Arrays.asList("ต้มยำกุ้ง", "ไก่ทอดกรอบ", "สปาเกตตีซอสมะเขือเทศ", "สลัดผักอาหารคลีน");
        Collections.shuffle(orderList); // สลับไพ่เมนูอาหารทั้งหมด

        System.out.println("=== ยินดีต้อนรับสู่ร้าน Little Chef ===");
        System.out.println("รับออร์เดอร์ ปรุงอาหาร และสะสมคะแนน!\n");

        // เล่น 3 ตา (ดึงเมนูจากที่สลับไว้แล้ว)
        for (int round = 1; round <= 3; round++) {
            String currentOrder = orderList.get(round - 1);
            System.out.println("----- ออร์เดอร์ที่ " + round + " -----");
            System.out.println("ลูกค้าสั่ง: ** " + currentOrder + " **");

            RecipeNode currentNode = root;

            while (!currentNode.isLeaf()) {
                System.out.println("\n" + currentNode.text);

                // ระบบรันเลขตัวเลือกอัตโนมัติ (i + 1)
                for (int i = 0; i < currentNode.choices.length; i++) {
                    System.out.println((i + 1) + ". " + currentNode.choices[i].text);
                }

                System.out.print("เลือกขั้นตอน (พิมพ์ตัวเลข): ");
                int choice = scanner.nextInt();

                if (choice >= 1 && choice <= currentNode.choices.length) {
                    currentNode = currentNode.choices[choice - 1];
                } else {
                    System.out.println("เชฟงง! ทำของตกพื้น... ลองเลือกใหม่");
                }
            }

            System.out.println("\n" + currentNode.text);
            if (currentNode.finalDish.equals(currentOrder)) {
                System.out.println("✅ ถูกต้อง! ลูกค้าประทับใจ ได้รับ 10 คะแนน");
                score += 10;
            } else {
                System.out.println("❌ ผิดเมนู! ลูกค้าโวยวาย ไม่ได้คะแนน");
            }
            System.out.println("------------------------\n");
        }

        System.out.println("จบเกม! คะแนนรวมของคุณคือ: " + score + "/30 คะแนน");
        scanner.close();
    }
}