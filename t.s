.globl _char1
	.data
_char1:
	.byte	1
.globl _short1
	.align 1
_short1:
	.word	1
.globl _int1
	.align 2
_int1:
	.long	1
.globl _long1
	.align 3
_long1:
	.quad	1
.globl _float1
	.align 2
_float1:
	.long	1065353216
.globl _double1
	.align 3
_double1:
	.long	0
	.long	1072693248
	.text
.globl _addf1
_addf1:
LFB2:
	pushq	%rbp
LCFI0:
	movq	%rsp, %rbp
LCFI1:
	movss	%xmm0, -4(%rbp)
	movss	-4(%rbp), %xmm1
	movss	LC0(%rip), %xmm0
	addss	%xmm1, %xmm0
	leave
	ret
LFE2:
.globl _addd1
_addd1:
LFB3:
	pushq	%rbp
LCFI2:
	movq	%rsp, %rbp
LCFI3:
	movsd	%xmm0, -8(%rbp)
	movsd	-8(%rbp), %xmm1
	movsd	LC1(%rip), %xmm0
	addsd	%xmm1, %xmm0
	leave
	ret
LFE3:
	.literal4
	.align 2
LC0:
	.long	1066192077
	.literal8
	.align 3
LC1:
	.long	1546188227
	.long	1072808591
	.section __TEXT,__eh_frame,coalesced,no_toc+strip_static_syms+live_support
EH_frame1:
	.set L$set$0,LECIE1-LSCIE1
	.long L$set$0
LSCIE1:
	.long	0x0
	.byte	0x1
	.ascii "zR\0"
	.byte	0x1
	.byte	0x78
	.byte	0x10
	.byte	0x1
	.byte	0x10
	.byte	0xc
	.byte	0x7
	.byte	0x8
	.byte	0x90
	.byte	0x1
	.align 3
LECIE1:
.globl _addf1.eh
_addf1.eh:
LSFDE1:
	.set L$set$1,LEFDE1-LASFDE1
	.long L$set$1
LASFDE1:
	.long	LASFDE1-EH_frame1
	.quad	LFB2-.
	.set L$set$2,LFE2-LFB2
	.quad L$set$2
	.byte	0x0
	.byte	0x4
	.set L$set$3,LCFI0-LFB2
	.long L$set$3
	.byte	0xe
	.byte	0x10
	.byte	0x86
	.byte	0x2
	.byte	0x4
	.set L$set$4,LCFI1-LCFI0
	.long L$set$4
	.byte	0xd
	.byte	0x6
	.align 3
LEFDE1:
.globl _addd1.eh
_addd1.eh:
LSFDE3:
	.set L$set$5,LEFDE3-LASFDE3
	.long L$set$5
LASFDE3:
	.long	LASFDE3-EH_frame1
	.quad	LFB3-.
	.set L$set$6,LFE3-LFB3
	.quad L$set$6
	.byte	0x0
	.byte	0x4
	.set L$set$7,LCFI2-LFB3
	.long L$set$7
	.byte	0xe
	.byte	0x10
	.byte	0x86
	.byte	0x2
	.byte	0x4
	.set L$set$8,LCFI3-LCFI2
	.long L$set$8
	.byte	0xd
	.byte	0x6
	.align 3
LEFDE3:
	.subsections_via_symbols
