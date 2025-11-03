import kotlin.math.abs

class PhanSo(var tu: Int, var mau: Int) {

    // Nhập phân số từ bàn phím
    fun nhap() {
        while (true) {
            print("Nhập tử số: ")
            tu = readln().toInt()
            print("Nhập mẫu số (khác 0): ")
            mau = readln().toInt()
            if (mau != 0) break
            println(" Error ")
        }
    }

    // In phân số ra màn hình
    fun xuat() {
        println("$tu/$mau")
    }

    // UCLN
    private fun gcd(a: Int, b: Int): Int {
        return if (b == 0) abs(a) else gcd(b, a % b)
    }

    // Tối giản
    fun toiGian() {
        val ucln = gcd(tu, mau)
        tu /= ucln
        mau /= ucln
        if (mau < 0) { // chuẩn hóa mẫu dương
            tu = -tu
            mau = -mau
        }
    }

    // So sánh với phân số
    fun soSanh(ps: PhanSo): Int {
        val left = tu * ps.mau
        val right = ps.tu * mau
        return when {
            left < right -> -1
            left == right -> 0
            else -> 1
        }
    }

    // Tính tổng phân số
    fun cong(ps: PhanSo): PhanSo {
        val tuMoi = tu * ps.mau + ps.tu * mau
        val mauMoi = mau * ps.mau
        val kq = PhanSo(tuMoi, mauMoi)
        kq.toiGian()
        return kq
    }
}

fun main() {
    print("Nhập số lượng phân số: ")
    val n = readln().toInt()
    val arr = Array(n) { PhanSo(1, 1) }

    // Nhập mảng phân số
    for (i in arr.indices) {
        println("➡ Nhập phân số thứ ${i + 1}:")
        arr[i].nhap()
    }

    println("\n--- Mảng phân số vừa nhập ---")
    arr.forEach { it.xuat() }

    // Tối giản từng phân số
    println("\n--- Mảng phân số sau khi tối giản ---")
    arr.forEach {
        it.toiGian()
        it.xuat()
    }

    // Tính tổng
    var tong = PhanSo(0, 1)
    for (ps in arr) {
        tong = tong.cong(ps)
    }
    println("\n--- Tổng các phân số ---")
    tong.xuat()

    // Tìm phân số lớn nhất
    var maxPS = arr[0]
    for (ps in arr) {
        if (ps.soSanh(maxPS) > 0) maxPS = ps
    }
    println("\n--- Phân số lớn nhất ---")
    maxPS.xuat()

    // Sắp xếp giảm dần
    val arrSapXep = arr.sortedWith { a, b -> b.soSanh(a) }
    println("\n--- Mảng sau khi sắp xếp giảm dần ---")
    arrSapXep.forEach { it.xuat() }
}
